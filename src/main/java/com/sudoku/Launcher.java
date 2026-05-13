package com.sudoku;

/**
 * Entry point launcher to bypass JavaFX runtime checks.
 * This class does not extend Application, allowing JavaFX to be loaded from the classpath.
 */
public class Launcher {
    /**
     * Main method that launches the JavaFX application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Main.main(args);
    }
}
