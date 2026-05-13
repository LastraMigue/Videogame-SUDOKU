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

---

## Diagrama de Secuencia: Flujo de Juego

Este diagrama muestra la interacción desde que el usuario selecciona una dificultad hasta que interactúa con el tablero.

```mermaid
sequenceDiagram
    actor Usuario
    participant Menu as MenuController
    participant Game as GameController
    participant Gen as SudokuGenerator
    participant Music as MusicManager

    Usuario->>Menu: Selecciona Dificultad
    Menu->>Music: playClickSound()
    Menu->>Game: Inicia Escena de Juego
    Game->>Gen: generate(Dificultad)
    Gen-->>Game: Tablero Inicial
    Game->>Usuario: Muestra Tablero
    
    Usuario->>Game: Introduce Número
    Game->>Music: playClickSound()
    Game->>Game: handleInput()
    Game-->>Usuario: Feedback Visual (Error/Éxito)
```

## Diagrama de Actividad: Lógica de Resolución (Solve)

Describe el comportamiento del sistema cuando el usuario pulsa el botón "SOLUCIONAR".

```mermaid
stateDiagram-v2
    [*] --> ResolverBase: Click en Solucionar
    ResolverBase --> IterarCeldas: Genera solución completa
    
    state IterarCeldas {
        [*] --> VerificarCelda
        VerificarCelda --> CeldaVacia: ¿Está vacía?
        VerificarCelda --> CeldaEscrita: ¿Tiene número?
        
        CeldaVacia --> MarcarFallo: Rellenar en Rojo
        CeldaEscrita --> CompararConSolucion
        
        CompararConSolucion --> MarcarAcierto: Es correcto (Verde)
        CompararConSolucion --> MarcarError: Es incorrecto (Rojo)
        
        MarcarFallo --> ProximaCelda
        MarcarAcierto --> ProximaCelda
        MarcarError --> ProximaCelda
        ProximaCelda --> [*]
    }
    
    IterarCeldas --> EvaluacionFinal
    EvaluacionFinal --> MostrarVictoria: 0 Errores
    EvaluacionFinal --> MostrarDerrota: Errores > 0
    MostrarVictoria --> [*]
    MostrarDerrota --> [*]
```
