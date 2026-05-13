# 🧩 SUDOKU — Premium Edition

![Sudoku Banner](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven)

Una experiencia de Sudoku definitiva, diseñada con una estética moderna, sonidos inmersivos y desafíos para todos los niveles, desde principiantes hasta expertos en el modo **Hardcore**.

---

## ✨ Características Principales

- 🎨 **Estética Premium**: Interfaz en modo oscuro con efectos de cristal y animaciones suaves.
- 🎵 **Sonido Dinámico**: Música ambiental y efectos de clic para una mayor inmersión.
- ⚙️ **Control Total**: Ajusta el volumen o reinicia la partida en cualquier momento.
- 🔴 **Detección de Conflictos**: Ayuda visual que resalta errores en tiempo real (desactivable).
- 🏆 **Modo Hardcore**: Un desafío real sin pistas, sin resaltado de errores y con el nivel más alto de dificultad.
- 💡 **Sistema de Pistas Inteligente**: Revela una casilla estratégica (máximo 3 por partida).
- ✅ **Validación Final**: Comprueba tu tablero con un código de colores (Verde para aciertos, Rojo para fallos).

---

## 🚀 Instalación y Ejecución

### Requisitos Previos
- **JDK 21** o superior.
- **Maven 3.8+**.

### Pasos
1. Clona el repositorio:
   ```bash
   git clone https://github.com/LastraMigue/Videogame-SUDOKU.git
   ```
2. Entra en la carpeta:
   ```bash
   cd Videogame-SUDOKU
   ```
3. Ejecuta el juego:
   ```bash
   mvn javafx:run
   ```

---

---

## 🛠️ Arquitectura

El proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)** desacoplado:

| Capa         | Paquete                  | Responsabilidad                       |
|--------------|--------------------------|---------------------------------------|
| Modelo       | `com.sudoku.model`       | `Board`, `Cell`, `SudokuValidator`    |
| Servicio     | `com.sudoku.service`     | `Generator`, `Solver`, `MusicManager` |
| Controlador  | `com.sudoku.controller`  | `Game`, `Menu`, `Settings`, `Result`  |
| Vista        | `com.sudoku.view`        | `BoardView` (renderizado JavaFX)      |

---

## 📂 Estructura del Proyecto

```
sudoku-javafx/
├── docs/                          ← Documentación (Javadoc, DESIGN.md, CHANGELOG.md)
├── src/
│   ├── main/java/com/sudoku/
│   │   ├── Main.java
│   │   ├── model/                 ← Lógica de datos del Sudoku
│   │   ├── service/               ← Algoritmos y gestión de música
│   │   ├── controller/            ← Controladores de escenas y overlays
│   │   └── view/                  ← Lógica de renderizado gráfico
│   ├── main/resources/
│   │   ├── com/sudoku/            ← Archivos FXML (Layouts)
│   │   ├── styles/                ← Hojas de estilo CSS
│   │   ├── music/                 ← Banda sonora y efectos (MP3)
│   │   └── images/                ← Icono de la aplicación
│   └── test/java/com/sudoku/      ← Tests unitarios con JUnit 5
├── README.md
└── pom.xml
```

---

## 📖 Documentación

Puedes encontrar más detalles sobre el diseño técnico en la carpeta `docs/`:
- [Guía de Diseño (DESIGN.md)](./docs/DESIGN.md)
- [Historial de Cambios (CHANGELOG.md)](./docs/CHANGELOG.md)

---

## ✒️ Autor
**LastraMigue** — *Desarrollador Principal*