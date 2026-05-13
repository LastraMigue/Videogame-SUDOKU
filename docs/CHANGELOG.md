# Historial de Cambios — SUDOKU

## [2.0.0]
### ✨ Novedades
- **Rediseño Estético Premium**: Nueva interfaz oscura con degradados, animaciones y tipografía moderna (Outfit).
- **Sistema de Audio**: Música de fondo persistente y efectos de sonido (clic) al interactuar con botones.
- **Modo HARDCORE**: Nuevo nivel de dificultad sin ayudas (pistas y resaltado de errores desactivados).
- **Panel de Ajustes**: Control de volumen en tiempo real mediante un overlay dinámico.
- **Validación Visual**: Al solucionar, los aciertos se marcan en verde y los errores/omisiones en rojo.
- **Pistas Limitadas**: Sistema de hasta 3 pistas por partida con contador en pantalla.
- **Overlays de Resultado**: Pantallas personalizadas de victoria y derrota con tiempo transcurrido.

### 🔧 Mejoras y Arreglos
- Restricción de entrada estricta en celdas (solo dígitos 1-9).
- Icono de aplicación personalizado y título unificado a "SUDOKU".
- Optimización de la lógica de detección de conflictos en tiempo real.
- Refactorización de tests unitarios para cubrir nuevas firmas de métodos.

---

## [1.0.0]
### 🚀 Funcionalidades Base
- **Generación Procedural**: Algoritmo de creación de tableros con solución única y niveles de dificultad (Fácil, Medio, Difícil).
- **Motor de Resolución**: Implementación de un `Solver` basado en el algoritmo de vuelta atrás (backtracking) para garantizar la jugabilidad.
- **Validación de Reglas**: Sistema de detección de conflictos en tiempo real para filas, columnas y cuadrículas de 3x3.
- **Interfaz JavaFX**: Rejilla de juego interactiva con soporte para entrada por teclado y ratón.

### ⚙️ Arquitectura y Calidad
- **Patrón MVC**: Estructura desacoplada para facilitar el mantenimiento y la escalabilidad del código.
- **Maven Multistage**: Gestión de dependencias y ciclo de vida de construcción automatizado.
- **Testing Exhaustivo**: Suite completa de pruebas unitarias con **JUnit 5** para validar la integridad de los tableros.
- **Control de Cobertura**: Integración de **JaCoCo** con una regla de cumplimiento del 80% de cobertura de código.
- **CI/CD**: Configuración de **GitHub Actions** para validación automática en cada integración.
