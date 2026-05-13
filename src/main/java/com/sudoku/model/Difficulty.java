package com.sudoku.model;

/**
 * Defines the game difficulty levels and the number of clues to reveal.
 *
 * @author LastraMigue
 * @version 1.0
 */
public enum Difficulty {
    /** Easy game: ~45-50 clues. */
    EASY(45),
    
    /** Medium game: ~30-35 clues. */
    MEDIUM(32),
    
    /** Hard game: ~20-25 clues. */
    HARD(22);

    private final int clues;

    Difficulty(int clues) {
        this.clues = clues;
    }

    /**
     * Returns the number of clues to keep on the board for this difficulty.
     *
     * @return number of initial clues
     */
    public int getClues() {
        return clues;
    }
}
