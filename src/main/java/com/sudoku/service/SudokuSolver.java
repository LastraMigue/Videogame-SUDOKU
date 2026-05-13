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
     * @param board the Sudoku board to solve
     * @return {@code true} if a solution was found and applied,
     *         {@code false} if the board is unsolvable
     */
    public boolean solve(Board board) {
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                // Find next empty cell
                if (board.getCell(row, col).isEmpty()) {
                    // Try values 1 to 9
                    for (int value = 1; value <= Board.SIZE; value++) {
                        if (validator.isValidPlacement(board, row, col, value)) {
                            board.setValue(row, col, value);

                            // Recursive step
                            if (solve(board)) {
                                return true;
                            }

                            // Backtrack if no solution found downstream
                            board.setValue(row, col, 0);
                        }
                    }
                    return false; // No valid number found for this cell
                }
            }
        }
        return true; // All cells are filled correctly
    }
}
