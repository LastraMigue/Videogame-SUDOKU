package com.sudoku.view;

import com.sudoku.model.Board;
import com.sudoku.model.Cell;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.function.BiConsumer;

/**
 * Handles the graphical representation of the Sudoku board.
 *
 * @author LastraMigue
 * @version 1.0
 */
public class BoardView {

    private final GridPane gridPane;
    private final TextField[][] textFields;

    /**
     * Creates a new BoardView and initializes the grid pane.
     */
    public BoardView() {
        this.gridPane = new GridPane();
        this.gridPane.getStyleClass().add("sudoku-grid");
        this.textFields = new TextField[Board.SIZE][Board.SIZE];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                TextField field = new TextField();
                field.getStyleClass().add("cell");
                
                // Aplicar bordes gruesos para cajas 3x3
                if (col == 2 || col == 5) field.getStyleClass().add("thick-right");
                if (row == 2 || row == 5) field.getStyleClass().add("thick-bottom");
                if ((col == 2 || col == 5) && (row == 2 || row == 5)) {
                    field.getStyleClass().remove("thick-right");
                    field.getStyleClass().remove("thick-bottom");
                    field.getStyleClass().add("thick-both");
                }

                textFields[row][col] = field;
                gridPane.add(field, col, row);
            }
        }
    }

    /**
     * Renders the state of the given board into the JavaFX grid.
     * Sets values, mutability, and error styles for each cell.
     *
     * @param board the board model to display
     */
    public void render(Board board) {
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Cell cell = board.getCell(r, c);
                TextField field = textFields[r][c];
                
                field.setText(cell.isEmpty() ? "" : String.valueOf(cell.getValue()));
                field.setEditable(!cell.isFixed());
                
                if (cell.isFixed()) {
                    field.getStyleClass().add("cell-fixed");
                } else {
                    field.getStyleClass().remove("cell-fixed");
                }
                
                updateErrorStyle(r, c, cell.hasError());
            }
        }
    }

    /**
     * Updates the visual error style for a specific cell.
     *
     * @param row      the cell's row index
     * @param col      the cell's column index
     * @param hasError whether the cell should show an error style
     */
    public void updateErrorStyle(int row, int col, boolean hasError) {
        if (hasError) {
            if (!textFields[row][col].getStyleClass().contains("cell-error")) {
                textFields[row][col].getStyleClass().add("cell-error");
            }
        } else {
            textFields[row][col].getStyleClass().remove("cell-error");
        }
    }

    /**
     * Registers a handler to be called whenever a cell value changes.
     *
     * @param handler a consumer that receives the row and column of the changed cell
     */
    public void setOnInputHandler(BiConsumer<Integer, Integer> handler) {
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                final int row = r;
                final int col = c;
                textFields[r][c].textProperty().addListener((obs, old, val) -> {
                    handler.accept(row, col);
                });
            }
        }
    }

    /**
     * Highlights the row, column, and 3x3 box of the selected cell.
     * @param row row index
     * @param col column index
     */
    public void highlightGroup(int row, int col) {
        clearHighlights();
        
        // Fila y Columna
        for (int i = 0; i < Board.SIZE; i++) {
            if (!textFields[row][i].getStyleClass().contains("cell-highlight")) {
                textFields[row][i].getStyleClass().add("cell-highlight");
            }
            if (!textFields[i][col].getStyleClass().contains("cell-highlight")) {
                textFields[i][col].getStyleClass().add("cell-highlight");
            }
        }
        
        // Bloque 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (!textFields[r][c].getStyleClass().contains("cell-highlight")) {
                    textFields[r][c].getStyleClass().add("cell-highlight");
                }
            }
        }
    }

    /**
     * Clears all temporary cell highlights.
     */
    public void clearHighlights() {
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                textFields[r][c].getStyleClass().remove("cell-highlight");
            }
        }
    }

    /**
     * Returns the underlying GridPane for layout insertion.
     * @return the JavaFX GridPane
     */
    public GridPane getGridPane() { return gridPane; }

    /**
     * Returns the 2D array of text fields for direct manipulation if needed.
     * @return the text field matrix
     */
    public TextField[][] getTextFields() { return textFields; }
}
