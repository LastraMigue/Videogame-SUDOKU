package com.sudoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase Board")
class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    @DisplayName("Tablero nuevo debe estar vacío")
    void newBoardIsEmpty() {
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                assertEquals(0, board.getValue(r, c));
            }
        }
    }

    @Test
    @DisplayName("Establecer y obtener valor correctamente")
    void setAndGetValue() {
        board.setValue(0, 0, 5);
        assertEquals(5, board.getValue(0, 0));
    }

    @Test
    @DisplayName("Lanzar excepción por coordenadas fuera de límites")
    void coordinatesOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> board.getValue(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> board.getValue(9, 0));
    }

    @Test
    @DisplayName("Identificar correctamente si el tablero está lleno")
    void isFullCheck() {
        assertFalse(board.isFull());
        // Llenamos el tablero de forma ficticia (no importa si es válido para este test)
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                board.setValue(r, c, 1);
            }
        }
        assertTrue(board.isFull());
    }

    @Test
    @DisplayName("Creación de tablero desde array inicial")
    void boardFromInitialArray() {
        int[][] initial = new int[9][9];
        initial[0][0] = 5;
        Board newBoard = new Board(initial);
        assertEquals(5, newBoard.getValue(0, 0));
        assertTrue(newBoard.getCell(0, 0).isFixed());
    }
}
