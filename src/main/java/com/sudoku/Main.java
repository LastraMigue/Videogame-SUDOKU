package com.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main entry point for the Sudoku JavaFX application.
 *
 * @author LastraMigue
 * @version 1.0
 */
public class Main extends Application {
    /**
     * Default constructor for the Main application class.
     */
    public Main() {}

    @Override
    public void start(Stage stage) throws IOException {
        // Iniciar msica
        com.sudoku.service.MusicManager.getInstance();
        
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        stage.setTitle("SUDOKU");
        
        // Cargar icono si existe
        try {
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        } catch (Exception e) {
            // Si no hay icono, se usa el de por defecto del sistema
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Main method to launch the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
