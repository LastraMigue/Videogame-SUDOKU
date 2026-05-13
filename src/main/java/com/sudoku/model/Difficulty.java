package com.sudoku.model;

/**
 * Defines the game difficulty levels and the number of clues to reveal.
 *
 * @author LastraMigue
 * @version 1.0
 */
public enum Difficulty {
    /** Easy game: ~45-50 clues. */
    EASY(45, "FÁCIL"),
    MEDIUM(32, "MEDIO"),
    HARD(22, "DIFÍCIL"),
    HARDCORE(22, "HARDCORE");

    private final int clues;
    private final String label;

    Difficulty(int clues, String label) {
        this.clues = clues;
        this.label = label;
    }

    public String getLabel() {
        return label;
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
