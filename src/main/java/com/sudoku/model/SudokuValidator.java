package com.sudoku.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides all validation logic for a Sudoku board.
 *
 * <p>This class checks whether a number can legally be placed at a given
 * position by verifying the three Sudoku constraints:</p>
 * <ol>
 *   <li>No duplicate in the same <b>row</b></li>
 *   <li>No duplicate in the same <b>column</b></li>
 *   <li>No duplicate in the same <b>3×3 box</b></li>
 * </ol>
 *
 * <p>All methods are stateless and operate only on the provided {@link Board}
 * reference, making this class safe to share across threads.</p>
 *
 * @author LastraMigue
 * @version 1.0
 * @see Board
 * @see Cell
 */
public class SudokuValidator {
    /**
     * Default constructor for the SudokuValidator.
     */
    public SudokuValidator() {}

    // -------------------------------------------------------------------------
    // Placement validation
    // -------------------------------------------------------------------------

    /**
     * Determines whether placing {@code value} at {@code (row, col)} is valid.
     *
     * <p>The check ignores the current value of the target cell, so it can be
     * used both for new placements and for re-validation after a change.</p>
     *
     * @param board the current board state
     * @param row   the zero-based target row (0–8)
     * @param col   the zero-based target column (0–8)
     * @param value the value to validate (1–9)
     * @return {@code true} if placing {@code value} at {@code (row, col)}
     *         violates none of the three Sudoku constraints
     */
    public boolean isValidPlacement(Board board, int row, int col, int value) {
        return isRowValid(board, row, value)
                && isColumnValid(board, col, value)
                && isBoxValid(board, row, col, value);
    }

    /**
     * Checks whether {@code value} already appears in the given row.
     *
     * @param board the board to inspect
     * @param row   the zero-based row index (0–8)
     * @param value the value to search for (1–9)
     * @return {@code true} if {@code value} does <em>not</em> appear in the row
     */
    public boolean isRowValid(Board board, int row, int value) {
        for (int col = 0; col < Board.SIZE; col++) {
            if (board.getValue(row, col) == value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether {@code value} already appears in the given column.
     *
     * @param board the board to inspect
     * @param col   the zero-based column index (0–8)
     * @param value the value to search for (1–9)
     * @return {@code true} if {@code value} does <em>not</em> appear in the column
     */
    public boolean isColumnValid(Board board, int col, int value) {
        for (int row = 0; row < Board.SIZE; row++) {
            if (board.getValue(row, col) == value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether {@code value} already appears in the 3×3 box
     * that contains the cell at {@code (row, col)}.
     *
     * @param board the board to inspect
     * @param row   the zero-based row of the target cell (0–8)
     * @param col   the zero-based column of the target cell (0–8)
     * @param value the value to search for (1–9)
     * @return {@code true} if {@code value} does <em>not</em> appear in the box
     */
    public boolean isBoxValid(Board board, int row, int col, int value) {
        int startRow = (row / Board.BOX_SIZE) * Board.BOX_SIZE;
        int startCol = (col / Board.BOX_SIZE) * Board.BOX_SIZE;
        for (int r = startRow; r < startRow + Board.BOX_SIZE; r++) {
            for (int c = startCol; c < startCol + Board.BOX_SIZE; c++) {
                if (board.getValue(r, c) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds all cells that conflict with the placement of {@code value} at {@code (row, col)}.
     *
     * @param board the current board state
     * @param row   the target row
     * @param col   the target column
     * @param value the value to check
     * @return a list of row/column pairs [r, c] representing conflicting cells
     */
    public List<int[]> getConflictingCells(Board board, int row, int col, int value) {
        List<int[]> conflicts = new ArrayList<>();
        if (value == 0) return conflicts;

        // Row conflicts
        for (int c = 0; c < Board.SIZE; c++) {
            if (c != col && board.getValue(row, c) == value) {
                conflicts.add(new int[]{row, c});
            }
        }

        // Col conflicts
        for (int r = 0; r < Board.SIZE; r++) {
            if (r != row && board.getValue(r, col) == value) {
                conflicts.add(new int[]{r, col});
            }
        }

        // Box conflicts
        int startRow = (row / Board.BOX_SIZE) * Board.BOX_SIZE;
        int startCol = (col / Board.BOX_SIZE) * Board.BOX_SIZE;
        for (int r = startRow; r < startRow + Board.BOX_SIZE; r++) {
            for (int c = startCol; c < startCol + Board.BOX_SIZE; c++) {
                if ((r != row || c != col) && board.getValue(r, c) == value) {
                    conflicts.add(new int[]{r, c});
                }
            }
        }
        return conflicts;
    }

    // -------------------------------------------------------------------------
    // Full-board validation
    // -------------------------------------------------------------------------

    /**
     * Checks whether the entire board is in a valid state according to
     * all Sudoku rules.
     *
     * <p>Empty cells (value == 0) are skipped during validation, so a
     * partially filled board can still be valid.</p>
     *
     * @param board the board to validate
     * @return {@code true} if no row, column, or box contains duplicate values
     */
    public boolean isBoardValid(Board board) {
        for (int i = 0; i < Board.SIZE; i++) {
            if (!isSetValid(getRow(board, i))) return false;
            if (!isSetValid(getColumn(board, i))) return false;
        }
        for (int br = 0; br < Board.BOX_SIZE; br++) {
            for (int bc = 0; bc < Board.BOX_SIZE; bc++) {
                if (!isSetValid(getBox(board, br * Board.BOX_SIZE, bc * Board.BOX_SIZE))) {
                    return false;
                }
            }
        }
        return true;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Checks whether an array of values (representing a row, column, or box)
     * contains any duplicate non-zero entries.
     *
     * @param values the array of cell values to inspect
     * @return {@code true} if there are no duplicates among non-zero values
     */
    private boolean isSetValid(int[] values) {
        boolean[] seen = new boolean[Board.SIZE + 1];
        for (int v : values) {
            if (v == 0) continue;
            if (seen[v]) return false;
            seen[v] = true;
        }
        return true;
    }

    /**
     * Extracts the values of all cells in the specified row.
     *
     * @param board the board to read from
     * @param row   the zero-based row index
     * @return an array of 9 cell values for that row
     */
    private int[] getRow(Board board, int row) {
        int[] result = new int[Board.SIZE];
        for (int c = 0; c < Board.SIZE; c++) {
            result[c] = board.getValue(row, c);
        }
        return result;
    }

    /**
     * Extracts the values of all cells in the specified column.
     *
     * @param board the board to read from
     * @param col   the zero-based column index
     * @return an array of 9 cell values for that column
     */
    private int[] getColumn(Board board, int col) {
        int[] result = new int[Board.SIZE];
        for (int r = 0; r < Board.SIZE; r++) {
            result[r] = board.getValue(r, col);
        }
        return result;
    }

    /**
     * Extracts the values of all cells in the 3×3 box starting at
     * {@code (startRow, startCol)}.
     *
     * @param board    the board to read from
     * @param startRow the top-left row of the box (0, 3, or 6)
     * @param startCol the top-left column of the box (0, 3, or 6)
     * @return an array of 9 cell values for that box
     */
    private int[] getBox(Board board, int startRow, int startCol) {
        int[] result = new int[Board.SIZE];
        int idx = 0;
        for (int r = startRow; r < startRow + Board.BOX_SIZE; r++) {
            for (int c = startCol; c < startCol + Board.BOX_SIZE; c++) {
                result[idx++] = board.getValue(r, c);
            }
        }
        return result;
    }
}
