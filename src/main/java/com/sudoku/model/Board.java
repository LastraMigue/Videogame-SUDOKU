package com.sudoku.model;

/**
 * Represents the 9×9 Sudoku board.
 *
 * <p>The board manages a grid of {@link Cell} objects and provides methods
 * for reading and writing values, checking completeness, and converting
 * the board to a raw integer array.</p>
 *
 * <p>Coordinate system: row and column indices are zero-based (0–8).</p>
 *
 * @author LastraMigue
 * @version 1.0
 * @see Cell
 * @see SudokuValidator
 */
public class Board {

    /** The number of rows and columns in a standard Sudoku board. */
    public static final int SIZE = 9;

    /** The side length of each 3×3 sub-box. */
    public static final int BOX_SIZE = 3;

    /** The internal grid of cells. */
    private final Cell[][] grid;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Creates an empty board where all cells are mutable and have value 0.
     */
    public Board() {
        grid = new Cell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                grid[row][col] = new Cell(0, false);
            }
        }
    }

    /**
     * Creates a board from a 2-dimensional integer array.
     *
     * <p>Non-zero values are treated as <b>fixed</b> (part of the original puzzle).
     * Zero values create mutable empty cells.</p>
     *
     * @param initialValues a 9×9 integer matrix with the initial board state;
     *                      values must be in the range 0–9
     * @throws IllegalArgumentException if the array does not have exactly 9 rows and 9 columns
     */
    public Board(int[][] initialValues) {
        if (initialValues.length != SIZE) {
            throw new IllegalArgumentException(
                    "Board must have exactly " + SIZE + " rows");
        }
        grid = new Cell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            if (initialValues[row].length != SIZE) {
                throw new IllegalArgumentException(
                        "Row " + row + " must have exactly " + SIZE + " columns");
            }
            for (int col = 0; col < SIZE; col++) {
                int val = initialValues[row][col];
                grid[row][col] = new Cell(val, val != 0);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Cell access
    // -------------------------------------------------------------------------

    /**
     * Returns the {@link Cell} at the specified position.
     *
     * @param row the zero-based row index (0–8)
     * @param col the zero-based column index (0–8)
     * @return the cell at {@code (row, col)}
     * @throws IllegalArgumentException if the coordinates are out of bounds
     */
    public Cell getCell(int row, int col) {
        validateCoordinates(row, col);
        return grid[row][col];
    }

    /**
     * Returns the integer value stored at the specified position.
     *
     * @param row the zero-based row index (0–8)
     * @param col the zero-based column index (0–8)
     * @return the value at {@code (row, col)} (0 = empty, 1–9 = filled)
     * @throws IllegalArgumentException if the coordinates are out of bounds
     */
    public int getValue(int row, int col) {
        return getCell(row, col).getValue();
    }

    /**
     * Sets the value of the cell at the specified position.
     *
     * @param row   the zero-based row index (0–8)
     * @param col   the zero-based column index (0–8)
     * @param value the value to assign (0–9)
     * @throws IllegalArgumentException if the coordinates are out of bounds
     * @throws IllegalStateException    if the target cell is fixed
     */
    public void setValue(int row, int col, int value) {
        getCell(row, col).setValue(value);
    }

    // -------------------------------------------------------------------------
    // Board state queries
    // -------------------------------------------------------------------------

    /**
     * Returns whether every cell on the board has a non-zero value.
     *
     * @return {@code true} if no cell is empty
     */
    public boolean isFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a deep copy of the board as a 2-dimensional integer array.
     *
     * <p>The returned array is independent of the board's internal state;
     * modifications to it will not affect the board.</p>
     *
     * @return a 9×9 int[][] snapshot of the current board values
     */
    public int[][] toIntArray() {
        int[][] result = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result[row][col] = grid[row][col].getValue();
            }
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Validates that the given row and column indices are within the board bounds.
     *
     * @param row the row index to validate
     * @param col the column index to validate
     * @throws IllegalArgumentException if either index is outside [0, SIZE)
     */
    private void validateCoordinates(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IllegalArgumentException(
                    "Coordinates out of bounds: (" + row + ", " + col + ")");
        }
    }
}
