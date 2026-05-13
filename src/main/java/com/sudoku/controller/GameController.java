package com.sudoku.controller;

import com.sudoku.Main;
import com.sudoku.model.Board;
import com.sudoku.model.Difficulty;
import com.sudoku.model.SudokuValidator;
import com.sudoku.service.SudokuGenerator;
import com.sudoku.service.SudokuSolver;
import com.sudoku.view.BoardView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.util.List;
import java.io.IOException;

/**
 * Main game controller. Coordinates the model, view and services.
 *
 * @author LastraMigue
 * @version 1.0
 */
public class GameController {
    /**
     * Default constructor for the GameController.
     */
    public GameController() {}

    @FXML private StackPane boardContainer;
    @FXML private Label timerLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label helpStatusLabel;

    private Board board;
    private BoardView boardView;
    private SudokuGenerator generator;
    private SudokuValidator validator;
    private SudokuSolver solver;
    
    private Timeline timeline;
    private int secondsElapsed = 0;
    private Difficulty currentDifficulty;
    private boolean helpMode = false;

    /**
     * Initializes the controller, sets up the board view and generator.
     * Automatically called by JavaFX.
     */
    public void initialize() {
        this.generator = new SudokuGenerator();
        this.validator = new SudokuValidator();
        this.solver = new SudokuSolver();
        this.boardView = new BoardView();
        
        boardContainer.getChildren().add(boardView.getGridPane());
        setupInputHandlers();
    }

    /**
     * Starts a new game with the specified difficulty.
     *
     * @param difficulty the difficulty level to generate
     */
    public void initGame(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
        this.board = generator.generate(difficulty);
        this.boardView.render(board);
        this.helpMode = false;
        updateStatusLabels();
        startTimer();
    }

    private void setupInputHandlers() {
        boardView.setOnInputHandler((row, col) -> {
            TextField field = boardView.getTextFields()[row][col];
            String text = field.getText();
            
            if (text.isEmpty()) {
                handleInput(row, col, 0);
            } else if (text.matches("[1-9]")) {
                int value = Integer.parseInt(text);
                handleInput(row, col, value);
            } else {
                // Evitar bucles infinitos: solo limpiar si no est ya vaco
                javafx.application.Platform.runLater(() -> field.setText(""));
                handleInput(row, col, 0);
            }
        });

        // Manejadores de foco para resaltar fila/columna/bloque en modo ayuda
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                final int row = r;
                final int col = c;
                boardView.getTextFields()[r][c].focusedProperty().addListener((obs, old, isFocused) -> {
                    if (isFocused && helpMode) {
                        boardView.highlightGroup(row, col);
                    } else {
                        boardView.clearHighlights();
                    }
                });
            }
        }
    }

    private void handleInput(int row, int col, int value) {
        try {
            board.setValue(row, col, value);
            
            if (helpMode) {
                refreshAllConflicts();
            } else {
                // Modo normal: sin ayudas visuales de error
                board.getCell(row, col).setError(false);
                boardView.updateErrorStyle(row, col, false);
            }
            
            if (board.isFull() && validator.isBoardValid(board)) {
                handleWin();
            }
        } catch (Exception e) {
            // Ignorar errores de celdas fijas
        }
    }

    private void refreshAllConflicts() {
        boolean[][] errorMap = new boolean[Board.SIZE][Board.SIZE];

        // 1. Encontrar todos los conflictos
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                int val = board.getValue(r, c);
                if (val != 0) {
                    List<int[]> conflicts = validator.getConflictingCells(board, r, c, val);
                    if (!conflicts.isEmpty()) {
                        errorMap[r][c] = true;
                        for (int[] conflict : conflicts) {
                            errorMap[conflict[0]][conflict[1]] = true;
                        }
                    }
                }
            }
        }

        // 2. Aplicar a la vista en el hilo de la UI
        javafx.application.Platform.runLater(() -> {
            for (int r = 0; r < Board.SIZE; r++) {
                for (int c = 0; c < Board.SIZE; c++) {
                    board.getCell(r, c).setError(errorMap[r][c]);
                    boardView.updateErrorStyle(r, c, errorMap[r][c]);
                }
            }
        });
    }

    @FXML
    private void handleToggleHelp() {
        this.helpMode = !this.helpMode;
        updateStatusLabels();
        if (helpMode) {
            refreshAllConflicts();
        } else {
            // Limpiar todos los errores visuales
            for (int r = 0; r < Board.SIZE; r++) {
                for (int c = 0; c < Board.SIZE; c++) {
                    board.getCell(r, c).setError(false);
                    boardView.updateErrorStyle(r, c, false);
                }
            }
        }
    }

    private void updateStatusLabels() {
        if (difficultyLabel != null) {
            difficultyLabel.setText("DIFICULTAD: " + currentDifficulty.getLabel());
        }
        if (helpStatusLabel != null) {
            helpStatusLabel.setText("AYUDA: " + (helpMode ? "ACTIVADA" : "DESACTIVADA"));
            helpStatusLabel.setStyle("-fx-text-fill: " + (helpMode ? "#a6e3a1" : "#f38ba8") + "; -fx-font-weight: bold;");
        }
    }

    private void handleWin() {
        stopTimer();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText("Has completado el Sudoku");
        alert.setContentText("Tiempo total: " + timerLabel.getText());
        alert.showAndWait();
    }

    @FXML
    private void handleHint() {
        // Implementación de pista: busca una celda vacía y la llena correctamente
        // usando el solver sobre una copia del tablero
        Board solution = new Board(board.toIntArray());
        if (solver.solve(solution)) {
            for (int r = 0; r < Board.SIZE; r++) {
                for (int c = 0; c < Board.SIZE; c++) {
                    if (board.getValue(r, c) == 0) {
                        int correctVal = solution.getValue(r, c);
                        board.setValue(r, c, correctVal);
                        boardView.render(board);
                        return;
                    }
                }
            }
        }
    }

    @FXML
    private void handleSolve() {
        if (solver.solve(board)) {
            boardView.render(board);
            stopTimer();
        }
    }

    @FXML
    private void handleReset() {
        initGame(currentDifficulty);
        secondsElapsed = 0;
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) throws IOException {
        stopTimer();
        
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    private void startTimer() {
        if (timeline != null) timeline.stop();
        secondsElapsed = 0;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsElapsed++;
            updateTimerLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopTimer() {
        if (timeline != null) timeline.stop();
    }

    private void updateTimerLabel() {
        int mins = secondsElapsed / 60;
        int secs = secondsElapsed % 60;
        timerLabel.setText(String.format("%02d:%02d", mins, secs));
    }
}
