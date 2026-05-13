# Sudoku JavaFX

> Juego de Sudoku completo construido con JavaFX 21 y Maven, siguiendo una arquitectura MVC estricta.

[![CI/CD](https://github.com/LastraMigue/Videogame-SUDOKU/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/LastraMigue/Videogame-SUDOKU/actions)
[![Cobertura](https://img.shields.io/badge/cobertura-80%25+-brightgreen)]()
[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/projects/jdk/21/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21.0.2-blue)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-red)](https://maven.apache.org/)

---

## Características

- 🎮 Tres niveles de dificultad: **Fácil**, **Medio**, **Difícil**
- ✅ Validación en tiempo real con resaltado de errores
- 💡 Sistema de pistas — revela una celda correcta cada vez
- 🤖 Resolvedor automático mediante backtracking recursivo
- 🎨 Interfaz moderna en JavaFX con estilos CSS
- ⏱️ Temporizador de partida
- 🔢 Generador de puzzles con solución única garantizada

---

## Requisitos

| Herramienta | Versión |
|-------------|---------|
| Java        | 21+     |
| Maven       | 3.9+    |
| JavaFX      | 21.0.2 (gestionado por Maven) |

---

## Ejecución

```bash
mvn javafx:run
```

## Tests

```bash
mvn test
```

## Informe de Cobertura

```bash
mvn test jacoco:report
# Informe generado en: target/site/jacoco/index.html
```

## Generar Javadoc

```bash
mvn javadoc:javadoc
# Documentación generada en: docs/javadoc/index.html
```

---

## Arquitectura

El proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)**:

| Capa         | Paquete                  | Responsabilidad                       |
|--------------|--------------------------|---------------------------------------|
| Modelo       | `com.sudoku.model`       | `Board`, `Cell`, `SudokuValidator`    |
| Servicio     | `com.sudoku.service`     | `SudokuGenerator`, `SudokuSolver`     |
| Controlador  | `com.sudoku.controller`  | `GameController`, `MenuController`    |
| Vista        | `com.sudoku.view`        | `BoardView` (renderizado JavaFX)      |

---

## Estructura del Proyecto

```
sudoku-javafx/
├── .github/workflows/ci-cd.yml   ← Pipeline CI/CD con GitHub Actions
├── docs/                          ← GitHub Pages (Javadoc + diagramas UML)
├── src/
│   ├── main/java/com/sudoku/
│   │   ├── Main.java
│   │   ├── model/                 ← Board, Cell, SudokuValidator
│   │   ├── service/               ← SudokuGenerator, SudokuSolver
│   │   ├── controller/            ← GameController, MenuController
│   │   └── view/                  ← BoardView
│   ├── main/resources/            ← FXML + CSS
│   └── test/java/com/sudoku/      ← Tests JUnit 5
├── README.md
├── CHANGELOG.md
└── pom.xml
```

---

## Documentación

- 📄 [GitHub Pages — Javadoc + Diagramas](https://lastramigue.github.io/Videogame-SUDOKU/)
- 📊 [Diagramas UML](docs/uml.md)
- 📝 [Historial de cambios](CHANGELOG.md)

---

## Estrategia de Ramas (GitFlow)

```
main          ← Solo releases estables. PROTEGIDA. Solo merge vía PR.
develop       ← Base de integración continua.
feature/*     ← Features individuales que se integran en develop.
release/*     ← Ramas de preparación de versión.
hotfix/*      ← Correcciones urgentes desde main.
```

---

## Convención de Commits

Todos los commits siguen [Conventional Commits](https://www.conventionalcommits.org/):

```
feat(board): add Cell class with immutability support
fix(validator): correct row validation for edge cases
test(solver): add backtracking unit tests
docs(uml): update class diagram
ci: add JaCoCo to GitHub Actions workflow
```