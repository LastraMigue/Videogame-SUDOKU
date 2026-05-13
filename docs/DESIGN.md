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

    Main ..> MusicManager : Inicia Singleton
    Main ..> MenuController : Carga FXML
    MenuController --> GameController : Instancia con Dificultad
    GameController o-- Board : Agrega
    GameController --> SudokuGenerator : Usa para crear
    GameController --> MusicManager : Llama para SFX
    Board "1" *-- "81" Cell : Compuesto por
```

---

## 2. Diagrama de Estados (Ciclo de Vida del Juego)
**Objetivo:** Definir las fases por las que pasa la aplicación y qué eventos disparan las transiciones.

```mermaid
stateDiagram-v2
    [*] --> MenuPrincipal
    MenuPrincipal --> Jugando: Seleccionar Dificultad
    
    state Jugando {
        [*] --> TableroInicial
        TableroInicial --> Editando: Usuario escribe
        Editando --> Editando: Validar conflicto
        Editando --> Configurando: Click en Ajustes
        Configurando --> Editando: Reanudar
    }
    
    Jugando --> Evaluando: Click en Solucionar
    Evaluando --> Victoria: 0 fallos
    Evaluando --> Derrota: > 0 fallos
    
    Victoria --> MenuPrincipal: Volver
    Derrota --> Jugando: Reiniciar
```

---

## 3. Diagrama de Secuencia (Inicialización de Partida)
**Objetivo:** Detallar el orden cronológico de los mensajes entre objetos durante el arranque.

```mermaid
sequenceDiagram
    participant U as Usuario
    participant M as MenuController
    participant S as MusicManager
    participant G as GameController
    participant Gen as SudokuGenerator

    U->>M: Elige dificultad (EASY)
    M->>S: playClickSound()
    M->>G: Cargar escena de juego
    activate G
    G->>Gen: generate(EASY)
    Gen-->>G: Board inicializado
    G->>G: startTimer()
    G-->>U: Muestra tablero listo
    deactivate G
```

---

## 4. Diagrama de Comunicación (Interacción de Componentes)
**Objetivo:** Visualizar cómo fluye la información y los comandos entre las piezas clave del sistema.

```mermaid
flowchart LR
    U((Usuario)) <--> UI[Interfaz JavaFX]
    UI -- Acciones FXML --> GC[GameController]
    GC -- Actualiza --> BV[BoardView]
    GC -- Consulta/Modifica --> BM[Board Model]
    GC -- Solicita Audio --> MM[MusicManager]
    BM -- Valida --> SV[SudokuValidator]
```

---

## 5. Diagrama de Actividad (Generación de Tablero)
**Objetivo:** Explicar el proceso lógico interno del `SudokuGenerator` para crear un reto válido.

```mermaid
flowchart TD
    A[Inicio] --> B[Crear Tablero Vacío]
    B --> C[Llenar con SudokuSolver]
    C --> D{¿Tablero Válido?}
    D -- No --> B
    D -- Sí --> E[Determinar celdas a eliminar]
    E --> F[Quitar N celdas según Dificultad]
    F --> G{¿Solución Única?}
    G -- No --> E
    G -- Sí --> H[Retornar Board al Controlador]
    H --> I[Fin]
```

---

## 6. Diagrama de Comportamiento (Entrada de Usuario)
**Objetivo:** Describir la respuesta del sistema ante la entrada de datos en una celda.

```mermaid
stateDiagram-v2
    [*] --> EsperandoEntrada
    EsperandoEntrada --> ValidandoFiltro: Usuario pulsa tecla
    
    state ValidandoFiltro {
        [*] --> ComprobarRegex
        ComprobarRegex --> Aceptar: Es [1-9]
        ComprobarRegex --> Rechazar: Otros caracteres
    }
    
    Aceptar --> ActualizarModelo: setValue()
    ActualizarModelo --> ComprobarConflicto: SudokuValidator
    ComprobarConflicto --> MarcarRojo: Hay duplicado
    ComprobarConflicto --> MarcarNormal: Sin errores
    Rechazar --> EsperandoEntrada: Ignorar tecla
```
