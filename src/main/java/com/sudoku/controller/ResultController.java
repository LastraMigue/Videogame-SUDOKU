package com.sudoku.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class ResultController {
    
    @FXML private Label statusLabel;
    @FXML private Label timeLabel;
    @FXML private Label messageLabel;
    @FXML private VBox resultCard;

    private GameController gameController;

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

    @FXML
    private void handleNewGame() {
        com.sudoku.service.MusicManager.getInstance().playClickSound();
        gameController.handleReset();
        close();
    }

    @FXML
    private void handleMenu() throws IOException {
        com.sudoku.service.MusicManager.getInstance().playClickSound();
        gameController.handleBackToMenu(null);
    }

    private void close() {
        gameController.closeResultOverlay();
    }
}
