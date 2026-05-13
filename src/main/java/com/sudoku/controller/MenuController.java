package com.sudoku.controller;

import com.sudoku.Main;
import com.sudoku.model.Difficulty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * Controller for the main menu view.
 * Handles difficulty selection and scene transitions.
 *
 * @author LastraMigue
 * @version 1.0
 */
public class MenuController {
    /**
     * Default constructor for the MenuController.
     */
    public MenuController() {}

    @FXML
    private void handleEasyMode(ActionEvent event) throws IOException {
        startGame(event, Difficulty.EASY);
    }

    @FXML
    private void handleMediumMode(ActionEvent event) throws IOException {
        startGame(event, Difficulty.MEDIUM);
    }

    @FXML
    private void handleHardMode(ActionEvent event) throws IOException {
        startGame(event, Difficulty.HARD);
    }

    private void startGame(ActionEvent event, Difficulty difficulty) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("game-view.fxml"));
        Scene scene = new Scene(loader.load());
        
        GameController gameController = loader.getController();
        gameController.initGame(difficulty);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }
}
