package com.sudoku.service;

import com.sudoku.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de SudokuSolver")
class SudokuSolverTest {

    private SudokuSolver solver;

    @BeforeEach
    void setUp() {
        solver = new SudokuSolver();
    }

    @Test
    @DisplayName("Resolver un tablero vacío")
    void solveEmptyBoard() {
        Board board = new Board();
        assertTrue(solver.solve(board));
        assertTrue(board.isFull());
    }

    @Test
    @DisplayName("Resolver un tablero parcialmente lleno (válido)")
    void solvePartialBoard() {
        int[][] initial = new int[9][9];
        initial[0][0] = 5;
        initial[0][1] = 3;
        initial[1][0] = 6;
        Board board = new Board(initial);
        
        assertTrue(solver.solve(board));
        assertTrue(board.isFull());
        assertEquals(5, board.getValue(0, 0)); // No debe cambiar los fijos
    }

    @Test
    @DisplayName("Fallar al resolver un tablero imposible")
    void solveImpossibleBoard() {
        int[][] initial = new int[9][9];
        // Ponemos dos 5 en la misma caja 3x3 de forma manual
        // Nota: El constructor de Board acepta cualquier valor, 
        // pero el solver no podrá encontrar solución.
        Board board = new Board();
        board.setValue(0, 0, 5);
        board.setValue(1, 1, 5); // Error de caja
        
        assertFalse(solver.solve(board));
    }
}
