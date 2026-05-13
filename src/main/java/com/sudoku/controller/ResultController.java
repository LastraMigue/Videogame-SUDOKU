package com.sudoku.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;

/**
 * Controller for the victory/defeat overlay.
 * Displays the game outcome and elapsed time.
 */
public class ResultController {
    
    @FXML private Label statusLabel;
    @FXML private Label timeLabel;
    @FXML private Label messageLabel;
    @FXML private VBox resultCard;

    private GameController gameController;

    /**
     * Initializes the result view with the outcome data.
     * @param victory true if the board was solved correctly
     * @param time formatted time string
     * @param gameController reference to the main game controller
     */
    public void setResult(boolean victory, String time, GameController gameController) {
        this.gameController = gameController;
        this.timeLabel.setText(time);
        
        if (victory) {
            statusLabel.setText("¡VICTORIA!");
            statusLabel.setStyle("-fx-text-fill: #a6e3a1; -fx-font-size: 32px; -fx-font-weight: bold;");
            messageLabel.setText("¡Felicidades! Has resuelto el Sudoku magistralmente.");
        } else {
            statusLabel.setText("DERROTA");
            statusLabel.setStyle("-fx-text-fill: #f38ba8; -fx-font-size: 32px; -fx-font-weight: bold;");
            messageLabel.setText("No has completado el tablero correctamente o te faltaron casillas.");
        }
    }

    /**
     * Resets the game board and closes the overlay.
     */
    @FXML
    private void handleNewGame() {
        com.sudoku.service.MusicManager.getInstance().playClickSound();
        gameController.handleReset();
        close();
    }

    /**
     * Navigates back to the main menu.
     * @throws IOException if the FXML for the menu cannot be loaded
     */
    @FXML
    private void handleMenu() throws IOException {
        com.sudoku.service.MusicManager.getInstance().playClickSound();
        gameController.handleBackToMenu(null);
    }

    private void close() {
        gameController.closeResultOverlay();
    }
}
