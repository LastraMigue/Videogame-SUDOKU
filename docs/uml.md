# Diagramas UML

En esta página se detallan los diagramas de arquitectura y flujo del proyecto, generados mediante **Mermaid**.

## Diagrama de Clases

El proyecto sigue una arquitectura **MVC** estricta.

```mermaid
classDiagram
    class Cell {
        -int value
        -boolean fixed
        -boolean hasError
        +Cell(int value, boolean fixed)
        +getValue() int
        +setValue(int value) void
        +isFixed() boolean
        +isEmpty() boolean
        +hasError() boolean
        +setError(boolean) void
    }

    class Board {
        +int SIZE = 9
        +int BOX_SIZE = 3
        -Cell[][] grid
        +Board()
        +Board(int[][] initialValues)
        +getCell(int, int) Cell
        +getValue(int, int) int
        +setValue(int, int, int) void
        +isFull() boolean
        +toIntArray() int[][]
    }

    class SudokuValidator {
        +isValidPlacement(Board, int, int, int) boolean
        +isRowValid(Board, int, int) boolean
        +isColumnValid(Board, int, int) boolean
        +isBoxValid(Board, int, int, int) boolean
        +isBoardValid(Board) boolean
    }

    class SudokuSolver {
        -SudokuValidator validator
        +solve(Board) boolean
    }

    class SudokuGenerator {
        -SudokuSolver solver
        -Random random
        +generate(Difficulty) Board
        -removeClues(Board, int) void
    }

    class GameController {
        -Board board
        -SudokuValidator validator
        -BoardView boardView
        +startNewGame(Difficulty) void
        +handleCellInput(int, int, int) void
        +checkWin() boolean
    }

    class BoardView {
        -GridPane grid
        -GameController controller
        +render(Board) void
        +highlightErrors(Board) void
    }

    Board "1" *-- "81" Cell : contiene
    SudokuSolver --> SudokuValidator : usa
    SudokuGenerator --> SudokuSolver : usa
    GameController --> Board : gestiona
    GameController --> SudokuValidator : usa
    GameController --> BoardView : actualiza
    BoardView --> GameController : notifica
```

## Diagrama de Actividad — Flujo del Juego

```mermaid
flowchart TD
    A([Inicio]) --> B[Mostrar Menú Principal]
    B --> C{Elección del Usuario}
    C -->|Nueva Partida| D[Seleccionar Dificultad]
    C -->|Salir| Z([Fin])
    D --> E[Generar Tablero]
    E --> F[Renderizar en JavaFX]
    F --> G{Acción del Jugador}
    G -->|Introducir Número| H[Validar Movimiento]
    H -->|Válido| I[Actualizar Celda]
    H -->|Inválido| J[Resaltar Error]
    J --> G
    I --> K{¿Tablero Lleno?}
    K -->|No| G
    K -->|Sí| L{¿Tablero Válido?}
    L -->|Sí| M[Mostrar Pantalla de Victoria]
    L -->|No| J
    M --> B
```

## Diagrama de Estados — Celda

```mermaid
stateDiagram-v2
    [*] --> Vacía : Creada con valor=0
    [*] --> Fija : Creada con valor!=0
    Vacía --> ConValor : setValue(1-9)
    ConValor --> Vacía : setValue(0)
    ConValor --> Error : Validación falla
    Error --> ConValor : Corrección aplicada
    Fija --> Fija : setValue() lanza excepción
```
