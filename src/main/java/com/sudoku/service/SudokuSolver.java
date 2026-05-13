package com.sudoku.service;

import com.sudoku.model.Board;
import com.sudoku.model.SudokuValidator;

/**
 * Solves a Sudoku puzzle using the recursive backtracking algorithm.
 *
 * <p>The solver attempts to fill empty cells one by one, ensuring each
 * placement adheres to Sudoku rules. If a placement leads to a dead end,
 * it backtracks and tries the next possible number.</p>
 *
 * @author LastraMigue
 * @version 1.0
 * @see Board
 * @see SudokuValidator
 */
public class SudokuSolver {

    /** Validator used to check if a placement is legal. */
    private final SudokuValidator validator;

    /**
     * Creates a new SudokuSolver with a default validator.
     */
    public SudokuSolver() {
        this.validator = new SudokuValidator();
    }

    /**
     * Attempts to solve the given board in-place.
     *
     * <p>First, it validates that the current board state doesn't violate any
     * rules. Then, it uses optimized backtracking to fill empty cells.</p>
     *
     * @param board the Sudoku board to solve
     * @return {@code true} if a solution was found and applied,
     *         {@code false} if the board is unsolvable or already invalid
     */
    public boolean solve(Board board) {
        if (!validator.isBoardValid(board)) {
            return false;
        }
        return solveRecursive(board, 0, 0);
    }

    /**
     * Recursive helper for the backtracking algorithm.
     *
     * @param board the board being solved
     * @param row   the current row to process
     * @param col   the current column to process
     * @return {@code true} if a solution is found from this state
     */
    private boolean solveRecursive(Board board, int row, int col) {
        // If we reached the end of the board, it's solved
        if (row == Board.SIZE) {
            return true;
        }

        // Calculate next cell coordinates
        int nextRow = (col == Board.SIZE - 1) ? row + 1 : row;
        int nextCol = (col == Board.SIZE - 1) ? 0 : col + 1;

        // Skip if cell is already filled
        if (!board.getCell(row, col).isEmpty()) {
            return solveRecursive(board, nextRow, nextCol);
        }

        // Try values 1 to 9
        for (int value = 1; value <= Board.SIZE; value++) {
            if (validator.isValidPlacement(board, row, col, value)) {
                board.setValue(row, col, value);

                if (solveRecursive(board, nextRow, nextCol)) {
                    return true;
                }

                // Backtrack
                board.setValue(row, col, 0);
            }
        }

        return false;
    }
}
