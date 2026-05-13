package com.sudoku.model;

/**
 * Represents a single cell in the Sudoku board.
 *
 * <p>A cell can be either <b>fixed</b> (given at the start of the puzzle,
 * cannot be modified) or <b>mutable</b> (player can enter and change values).
 * It also tracks whether the current value produces a validation error.</p>
 *
 * @author LastraMigue
 * @version 1.0
 * @see Board
 */
public class Cell {

    /** The numeric value stored in this cell (0 = empty, 1-9 = filled). */
    private int value;

    /** Whether this cell was part of the original puzzle and cannot be changed. */
    private final boolean fixed;

    /** Whether the current value conflicts with Sudoku rules. */
    private boolean hasError;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Creates a new Cell with the specified value and mutability.
     *
     * @param value the initial value (0 = empty, 1–9 = filled)
     * @param fixed {@code true} if the cell is part of the original puzzle
     *              and cannot be modified by the player
     */
    public Cell(int value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
        this.hasError = false;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    /**
     * Returns the numeric value of this cell.
     *
     * @return the cell value (0 = empty, 1–9 = filled)
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns whether this cell belongs to the original puzzle definition
     * and is therefore immutable during gameplay.
     *
     * @return {@code true} if the cell is fixed
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * Returns whether this cell is currently empty (value == 0).
     *
     * @return {@code true} if the cell has no value assigned
     */
    public boolean isEmpty() {
        return value == 0;
    }

    /**
     * Returns whether the current value of this cell violates Sudoku rules.
     *
     * @return {@code true} if the cell is in an error state
     */
    public boolean hasError() {
        return hasError;
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    /**
     * Sets the numeric value of this cell.
     *
     * <p>Fixed cells cannot be modified. Values must be in the range 0–9,
     * where 0 represents an empty cell.</p>
     *
     * @param value the new value to assign (0–9)
     * @throws IllegalStateException    if this cell is fixed
     * @throws IllegalArgumentException if the value is outside the range 0–9
     */
    public void setValue(int value) {
        if (fixed) {
            throw new IllegalStateException("Cannot modify a fixed cell");
        }
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException(
                    "Cell value must be between 0 and 9, got: " + value);
        }
        this.value = value;
    }

    /**
     * Sets the error state of this cell.
     *
     * <p>This flag is toggled by the validator layer to indicate whether
     * the current value breaks any Sudoku constraint.</p>
     *
     * @param hasError {@code true} to mark the cell as invalid
     */
    public void setError(boolean hasError) {
        this.hasError = hasError;
    }

    // -------------------------------------------------------------------------
    // Object overrides
    // -------------------------------------------------------------------------

    /**
     * Returns a single-character string representation of this cell.
     *
     * @return {@code "."} if empty, otherwise the digit as a string
     */
    @Override
    public String toString() {
        return value == 0 ? "." : String.valueOf(value);
    }
}
