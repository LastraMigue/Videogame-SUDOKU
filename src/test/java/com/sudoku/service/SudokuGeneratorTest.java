package com.sudoku.service;

import com.sudoku.model.Board;
import com.sudoku.model.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de SudokuGenerator")
class SudokuGeneratorTest {

    private SudokuGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new SudokuGenerator();
    }

    @Test
    @DisplayName("Generar tablero según dificultad")
    void generateDifficultyLevels() {
        Board easy = generator.generate(Difficulty.EASY);
        Board hard = generator.generate(Difficulty.HARD);
        
        assertNotNull(easy);
        assertNotNull(hard);
        
        // El tablero difícil debería tener menos pistas (más ceros)
        int easyZeros = countZeros(easy);
        int hardZeros = countZeros(hard);
        
        assertTrue(hardZeros > easyZeros, "El nivel difícil debería tener más celdas vacías");
    }

    private int countZeros(Board board) {
        int zeros = 0;
        for (int r = 0; r < Board.SIZE; r++)
            for (int c = 0; c < Board.SIZE; c++)
                if (board.getValue(r, c) == 0) zeros++;
        return zeros;
    }
}
