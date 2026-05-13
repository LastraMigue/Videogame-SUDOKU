package com.sudoku.controller;

import com.sudoku.Main;
import com.sudoku.model.Board;
import com.sudoku.service.MusicManager;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
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
 * @author LastraMigue
 * @version 2.0
 */
public class GameController {
    /**
     * Default constructor for the GameController.
     */
    public GameController() {}

    @FXML private StackPane rootPane;
    @FXML private StackPane boardContainer;
    @FXML private Button hintButton;
    @FXML private Button helpButton;
    @FXML private Label hintsLabel;
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
    private int hintsRemaining = 3;
    private Difficulty currentDifficulty;
    private boolean helpMode = false;
    private boolean isAutoFilling = false;

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
        
        // Bloquear Ayuda y Pista en modo HARDCORE
        boolean isHardcore = (difficulty == Difficulty.HARDCORE);
        if (hintButton != null) hintButton.setDisable(isHardcore);
        if (helpButton != null) helpButton.setDisable(isHardcore);
        
        this.hintsRemaining = isHardcore ? 0 : 3;
        updateStatusLabels();
        startTimer();
    }

    private void setupInputHandlers() {
        boardView.setOnInputHandler((row, col) -> {
            TextField field = boardView.getTextFields()[row][col];
            String text = field.getText();
            int value = text.isEmpty() ? 0 : Integer.parseInt(text);
            handleInput(row, col, value);
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
            
            if (!isAutoFilling && board.isFull() && validator.isBoardValid(board)) {
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

    /**
     * Toggles the help mode (real-time error highlighting).
     * Disabled in Hardcore mode.
     */
    @FXML
    private void handleToggleHelp() {
        MusicManager.getInstance().playClickSound();
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

    @FXML
    private void handleOpenSettings() throws IOException {
        MusicManager.getInstance().playClickSound();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("settings-view.fxml"));
        StackPane settingsOverlay = loader.load();
        
        SettingsController controller = loader.getController();
        controller.setContainers(rootPane, settingsOverlay);
        
        rootPane.getChildren().add(settingsOverlay);
    }

    /**
     * Updates the status labels in the UI (Difficulty, Hints, Help).
     */
    private void updateStatusLabels() {
        if (difficultyLabel != null) {
            difficultyLabel.setText("DIFICULTAD: " + currentDifficulty.getLabel());
        }
        if (helpStatusLabel != null) {
            helpStatusLabel.setText("AYUDA: " + (helpMode ? "ACTIVADA" : "DESACTIVADA"));
            helpStatusLabel.setStyle("-fx-text-fill: " + (helpMode ? "#a6e3a1" : "#f38ba8") + "; -fx-font-weight: bold;");
        }
        if (hintsLabel != null) {
            hintsLabel.setText("PISTAS: " + hintsRemaining);
        }
    }

    private void handleWin() {
        stopTimer();
        showResultOverlay(true);
    }

    /**
     * Handles the hint action. Reveals a random correct number in an empty cell.
     * Costs one hint.
     */
    @FXML
    private void handleHint() {
        MusicManager.getInstance().playClickSound();
        if (hintsRemaining <= 0) return;

        Board solution = new Board(board.toIntArray());
        if (solver.solve(solution)) {
            List<int[]> emptyCells = new ArrayList<>();
            for (int r = 0; r < Board.SIZE; r++) {
                for (int c = 0; c < Board.SIZE; c++) {
                    if (board.getValue(r, c) == 0) {
                        emptyCells.add(new int[]{r, c});
                    }
                }
            }

            if (!emptyCells.isEmpty()) {
                int[] picked = emptyCells.get(new java.util.Random().nextInt(emptyCells.size()));
                int r = picked[0];
                int c = picked[1];
                int correctVal = solution.getValue(r, c);
                
                isAutoFilling = true;
                board.setValue(r, c, correctVal);
                boardView.render(board);
                isAutoFilling = false;
                
                hintsRemaining--;
                updateStatusLabels();
                if (hintsRemaining <= 0) {
                    hintButton.setDisable(true);
                }
            }
        }
    }

    /**
     * Validates the entire board and shows the result overlay.
     * Highlights correct cells in green and incorrect/empty ones in red.
     */
    @FXML
    private void handleSolve() {
        MusicManager.getInstance().playClickSound();
        isAutoFilling = true;
        int[][] onlyFixed = new int[Board.SIZE][Board.SIZE];
        for(int r=0; r<Board.SIZE; r++) {
            for(int c=0; c<Board.SIZE; c++) {
                if(board.getCell(r, c).isFixed()) onlyFixed[r][c] = board.getValue(r, c);
            }
        }
        Board solutionBoard = new Board(onlyFixed);
        
        if (solver.solve(solutionBoard)) {
            int errorsCount = 0;
            for (int r = 0; r < Board.SIZE; r++) {
                for (int c = 0; c < Board.SIZE; c++) {
                    int userVal = board.getValue(r, c);
                    int correctVal = solutionBoard.getValue(r, c);
                    TextField field = boardView.getTextFields()[r][c];
                    
                    if (userVal != 0) {
                        if (userVal == correctVal) {
                            field.getStyleClass().add("cell-correct");
                        } else {
                            field.getStyleClass().add("cell-incorrect");
                            errorsCount++;
                        }
                    } else {
                        // Casilla vaca: cuenta como fallo
                        board.setValue(r, c, correctVal);
                        field.setText(String.valueOf(correctVal));
                        field.getStyleClass().add("cell-incorrect");
                        errorsCount++;
                    }
                }
            }
            stopTimer();
            isAutoFilling = false;
            showResultOverlay(errorsCount == 0);
        } else {
            isAutoFilling = false;
        }
    }

    private StackPane currentResultOverlay;

    /**
     * Shows the result overlay with a custom message based on victory status.
     * @param victory true if the user solved the puzzle without errors
     */
    private void showResultOverlay(boolean victory) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("result-view.fxml"));
            currentResultOverlay = loader.load();
            
            ResultController controller = loader.getController();
            controller.setResult(victory, timerLabel.getText(), this);
            
            rootPane.getChildren().add(currentResultOverlay);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the current result overlay.
     */
    public void closeResultOverlay() {
        if (currentResultOverlay != null) {
            rootPane.getChildren().remove(currentResultOverlay);
            currentResultOverlay = null;
        }
    }

    @FXML
    public void handleReset() {
        MusicManager.getInstance().playClickSound();
        initGame(currentDifficulty);
        secondsElapsed = 0;
    }

    @FXML
    public void handleBackToMenu(ActionEvent event) throws IOException {
        MusicManager.getInstance().playClickSound();
        stopTimer();
        
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage;
        if (event != null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            stage = (Stage) rootPane.getScene().getWindow();
        }
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
