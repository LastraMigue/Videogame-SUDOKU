package com.sudoku.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase Cell")
class CellTest {

    @Test
    @DisplayName("Crear celda vacía correctamente")
    void createEmptyCell() {
        Cell cell = new Cell(0, false);
        assertEquals(0, cell.getValue());
        assertFalse(cell.isFixed());
        assertTrue(cell.isEmpty());
    }

    @Test
    @DisplayName("Establecer valor en celda mutable")
    void setValidValue() {
        Cell cell = new Cell(0, false);
        cell.setValue(5);
        assertEquals(5, cell.getValue());
    }

    @Test
    @DisplayName("Lanzar excepción al modificar celda fija")
    void modifyFixedCellThrowsException() {
        Cell cell = new Cell(5, true);
        assertThrows(IllegalStateException.class, () -> cell.setValue(3));
    }

    @Test
    @DisplayName("Lanzar excepción con valores fuera de rango")
    void invalidValueThrowsException() {
        Cell cell = new Cell(0, false);
        assertThrows(IllegalArgumentException.class, () -> cell.setValue(10));
        assertThrows(IllegalArgumentException.class, () -> cell.setValue(-1));
    }
}
