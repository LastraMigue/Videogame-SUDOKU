package com.sudoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase SudokuValidator")
class SudokuValidatorTest {

    private Board board;
    private SudokuValidator validator;

    @BeforeEach
    void setUp() {
        board = new Board();
        validator = new SudokuValidator();
    }

    @Test
    @DisplayName("Validación de fila: detectar duplicados")
    void rowValidation() {
        board.setValue(0, 0, 5);
        assertFalse(validator.isRowValid(board, 0, 5, 5), "No debería permitir un 5 en la misma fila");
        assertTrue(validator.isRowValid(board, 0, 5, 3), "Debería permitir un 3 en la fila");
    }

    @Test
    @DisplayName("Validación de columna: detectar duplicados")
    void columnValidation() {
        board.setValue(0, 0, 7);
        assertFalse(validator.isColumnValid(board, 5, 0, 7), "No debería permitir un 7 en la misma columna");
        assertTrue(validator.isColumnValid(board, 5, 0, 2), "Debería permitir un 2 en la columna");
    }

    @Test
    @DisplayName("Validación de caja 3x3: detectar duplicados")
    void boxValidation() {
        board.setValue(0, 0, 9);
        assertFalse(validator.isBoxValid(board, 1, 1, 9), "No debería permitir un 9 en la misma caja 3x3");
        assertTrue(validator.isBoxValid(board, 1, 1, 4), "Debería permitir un 4 en la caja");
    }

    @Test
    @DisplayName("isValidPlacement coordina todas las reglas")
    void fullPlacementValidation() {
        board.setValue(0, 0, 1);
        assertFalse(validator.isValidPlacement(board, 0, 5, 1), "Falla por fila");
        assertFalse(validator.isValidPlacement(board, 5, 0, 1), "Falla por columna");
        assertFalse(validator.isValidPlacement(board, 1, 1, 1), "Falla por caja");
        assertTrue(validator.isValidPlacement(board, 5, 5, 1), "Debería ser válido aquí");
    }

    @Test
    @DisplayName("Detectar si un tablero completo es válido o no")
    void isBoardValidCheck() {
        // Tablero vacío es válido
        assertTrue(validator.isBoardValid(board));
        
        // Tablero con error manual
        board.setValue(0, 0, 5);
        board.setValue(0, 1, 5);
        assertFalse(validator.isBoardValid(board));
    }

    @Test
    @DisplayName("Validar integridad de un tablero real (fragmento)")
    void realBoardFragment() {
        int[][] values = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        Board realBoard = new Board(values);
        assertTrue(validator.isBoardValid(realBoard));
    }
}
