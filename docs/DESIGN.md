# Arquitectura de Diseño — SUDOKU

El proyecto sigue una arquitectura **MVC (Modelo-Vista-Controlador)** desacoplada, utilizando servicios especializados para la lógica compleja.

## Diagrama de Clases

```mermaid
classDiagram
    direction TB
    
    class Main {
        +start(Stage stage)
    }
    
    class MusicManager {
        <<Singleton>>
        -mediaPlayer: MediaPlayer
        -clickSound: AudioClip
        +getInstance()
        +setVolume(double)
        +playClickSound()
    }
    
    namespace Model {
        class Board {
            -cells: Cell[][]
            +getValue(r, c)
            +setValue(r, c, v)
            +isFull()
        }
        class Cell {
            -value: int
            -fixed: boolean
            -error: boolean
        }
        class Difficulty {
            <<Enum>>
            EASY, MEDIUM, HARD, HARDCORE
        }
    }
    
    namespace Controller {
        class GameController {
            -board: Board
            -boardView: BoardView
            +handleSolve()
            +handleHint()
        }
        class MenuController {
            +startGame()
        }
        class SettingsController {
            +handleVolumeChange()
        }
    }
    
    namespace View {
        class BoardView {
            -gridPane: GridPane
            -textFields: TextField[][]
            +render(Board)
            +highlightGroup(r, c)
        }
    }
    
    Main --> MusicManager : Inicia
    GameController --> Board : Gestiona
    GameController --> BoardView : Actualiza
    MenuController ..> GameController : Lanza
    SettingsController --> MusicManager : Configura
```

## Patrones de Diseño Aplicados

1.  **Singleton**: `MusicManager` asegura que la música no se reinicie al cambiar de escena.
2.  **MVC**: Separación estricta entre `Board` (Lógica), `BoardView` (Interfaz) y `GameController` (Orquestación).
3.  **Strategy**: El sistema de generación utiliza diferentes densidades de celdas según el nivel de `Difficulty`.
