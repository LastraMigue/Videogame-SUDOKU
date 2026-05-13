# Historial de Cambios

Todos los cambios relevantes en este proyecto se documentan en este archivo.

El formato se basa en [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)
y el proyecto sigue [Versionado Semántico](https://semver.org/spec/v2.0.0.html).

---

## [Sin publicar]

### Añadido
- Ninguno.

---

## [1.0.0] — 2026-05-13

### Añadido
- `Cell.java` — modelo de celda con soporte de inmutabilidad y estado de error
- `Board.java` — tablero 9×9 que gestiona objetos `Cell` con validación de coordenadas
- `SudokuValidator.java` — lógica de validación por fila, columna y caja 3×3
- `SudokuGenerator.java` — generador de puzzles por backtracking con solución única garantizada
- `SudokuSolver.java` — resolvedor recursivo por backtracking
- `GameController.java` — lógica de juego: entrada del usuario, pistas, detección de victoria
- `MenuController.java` — menú principal y selección de dificultad
- `BoardView.java` — renderizado JavaFX del tablero con resaltado de errores en tiempo real
- `Main.java` — punto de entrada de la aplicación JavaFX
- Layouts FXML: `menu-view.fxml`, `game-view.fxml`
- Tema CSS: `sudoku.css`
- Suite de tests JUnit 5: `BoardTest`, `CellTest`, `SudokuValidatorTest`, `SudokuSolverTest`
- Configuración de cobertura JaCoCo (≥ 80%)
- Pipeline CI/CD con GitHub Actions: compilación, tests, Javadoc y despliegue a GitHub Pages
- Diagramas UML Mermaid en `docs/uml.md`

### Corregido
- `hotfix/timer-null-check` — NullPointerException al detener el temporizador antes de su inicialización

---

## [0.1.0] — 2026-05-13

### Añadido
- Configuración inicial del repositorio con GitFlow
- `pom.xml` con JavaFX 21, JUnit 5, JaCoCo y Javadoc
- `.gitignore` con exclusiones para Maven, Java e IDEs

[Sin publicar]: https://github.com/LastraMigue/Videogame-SUDOKU/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/LastraMigue/Videogame-SUDOKU/releases/tag/v1.0.0
[0.1.0]: https://github.com/LastraMigue/Videogame-SUDOKU/releases/tag/v0.1.0
