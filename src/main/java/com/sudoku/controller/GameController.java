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

import java.io.IOException;

/**
 * Main game controller. Coordinates the model, view and services.
 *
 * @author LastraMigue
 * @version 1.0
 */
public class GameController {

    @FXML private StackPane boardContainer;
    @FXML private Label timerLabel;

    private Board board;
    private BoardView boardView;
    private SudokuGenerator generator;
    private SudokuValidator validator;
    private SudokuSolver solver;
    
    private Timeline timeline;
    private int secondsElapsed = 0;
    private Difficulty currentDifficulty;

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
        startTimer();
    }

    private void setupInputHandlers() {
        boardView.setOnInputHandler((row, col) -> {
            TextField field = boardView.getTextFields()[row][col];
            String text = field.getText();
            
            if (text.matches("[1-9]")) {
                int value = Integer.parseInt(text);
                handleInput(row, col, value);
            } else {
                field.setText("");
                board.setValue(row, col, 0);
            }
        });
    }

    private void handleInput(int row, int col, int value) {
        try {
            board.setValue(row, col, value);
            boolean isValid = validator.isValidPlacement(board, row, col, value);
            board.getCell(row, col).setError(!isValid);
            boardView.updateErrorStyle(row, col, !isValid);
            
            if (board.isFull() && validator.isBoardValid(board)) {
                handleWin();
            }
        } catch (Exception e) {
            // Ignorar errores de celdas fijas (no deberían ocurrir por setEditable(false))
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
        // BUG INTENCIONADO: No detenemos el timeline aquí, 
        // lo que causará fugas de memoria y múltiples hilos si volvemos a jugar.
        // Se arreglará en la rama hotfix.
        
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(loader.load(), 600, 700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
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
