# Arquitectura y Diseño Técnico — SUDOKU

Este documento detalla la estructura, comportamiento e interacciones del sistema mediante diversos diagramas técnicos que ilustran la robustez de la arquitectura **MVC**.

---

## 1. Diagrama de Clases (Estructura Estática)
**Objetivo:** Mostrar la jerarquía de clases, sus métodos principales y cómo se relacionan entre sí.

```mermaid
classDiagram
    direction TB
    
    class Main {
        +start(Stage stage)
        +main(String[] args)
    }
    
    class MusicManager {
        <<Singleton>>
        -MediaPlayer mediaPlayer
        -AudioClip clickSound
        +getInstance() MusicManager
        +setVolume(double vol)
        +playClickSound()
        +pause()
    }
    
    class Board {
        -Cell[][] cells
        +getValue(int r, int c) int
        +setValue(int r, int c, int v)
        +isFull() boolean
        +toIntArray() int[][]
    }
    
    class GameController {
        -Board board
        -BoardView boardView
        -SudokuSolver solver
        +handleSolve()
        +handleHint()
        +handleInput()
    }
    
    class SudokuGenerator {
        +generate(Difficulty d) Board
    }

    Main ..> MusicManager : Initialize Singleton
    Main ..> MenuController : Load FXML
    MenuController --> GameController : Instantiate with Difficulty
    GameController o-- Board : Aggregate
    GameController --> SudokuGenerator : Use for creation
    GameController --> MusicManager : Call for SFX
    Board "1" *-- "81" Cell : Composed of
```

---

## 2. Diagrama de Estados (Ciclo de Vida del Juego)
**Objetivo:** Definir las fases por las que pasa la aplicación y qué eventos disparan las transiciones.

```mermaid
stateDiagram-v2
    [*] --> MainMenu
    MainMenu --> Playing: Select Difficulty
    
    state Playing {
        [*] --> InitialBoard
        InitialBoard --> Editing: User input
        Editing --> Editing: Validate conflict
        Editing --> Settings: Click Settings
        Settings --> Editing: Resume
    }
    
    Playing --> Evaluating: Click Solve
    Evaluating --> Victory: 0 errors
    Evaluating --> Defeat: > 0 errors
    
    Victory --> MainMenu: Back
    Defeat --> Playing: Restart
```

---

## 3. Diagrama de Secuencia (Inicialización de Partida)
**Objetivo:** Detallar el orden cronológico de los mensajes entre objetos durante el arranque.

```mermaid
sequenceDiagram
    participant U as User
    participant M as MenuController
    participant S as MusicManager
    participant G as GameController
    participant Gen as SudokuGenerator

    U->>M: Choose difficulty (EASY)
    M->>S: playClickSound()
    M->>G: Load game scene
    activate G
    G->>Gen: generate(EASY)
    Gen-->>G: Board initialized
    G->>G: startTimer()
    G-->>U: Show ready board
    deactivate G
```

---

## 4. Diagrama de Comunicación (Interacción de Componentes)
**Objetivo:** Visualizar cómo fluye la información y los comandos entre las piezas clave del sistema.

```mermaid
flowchart LR
    U((User)) <--> UI[JavaFX Interface]
    UI -- FXML Actions --> GC[GameController]
    GC -- Update --> BV[BoardView]
    GC -- Query/Modify --> BM[Board Model]
    GC -- Request Audio --> MM[MusicManager]
    BM -- Validate --> SV[SudokuValidator]
```

---

## 5. Diagrama de Actividad (Generación de Tablero)
**Objetivo:** Explicar el proceso lógico interno del `SudokuGenerator` para crear un reto válido.

```mermaid
flowchart TD
    A[Start] --> B[Create Empty Board]
    B --> C[Fill with SudokuSolver]
    C --> D{Valid Board?}
    D -- No --> B
    D -- Yes --> E[Determine cells to remove]
    E --> F[Remove N cells by Difficulty]
    F --> G{Unique Solution?}
    G -- No --> E
    G -- Yes --> H[Return Board to Controller]
    H --> I[End]
```

---

## 6. Diagrama de Comportamiento (Entrada de Usuario)
**Objetivo:** Describir la respuesta del sistema ante la entrada de datos en una celda.

```mermaid
stateDiagram-v2
    [*] --> WaitingForInput
    WaitingForInput --> ValidatingFilter: User key press
    
    state ValidatingFilter {
        [*] --> CheckRegex
        CheckRegex --> Accept: Is [1-9]
        CheckRegex --> Reject: Other characters
    }
    
    Accept --> UpdateModel: setValue()
    UpdateModel --> CheckConflict: SudokuValidator
    CheckConflict --> MarkRed: Duplicate found
    CheckConflict --> MarkNormal: No errors
    Reject --> WaitingForInput: Ignore key
```
