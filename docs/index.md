# Documentación Técnica — SUDOKU

Bienvenido a la documentación técnica oficial de **SUDOKU**. Este sitio se genera automáticamente mediante **GitHub Actions**.

## Enlaces Rápidos

- 📚 [Referencia API Javadoc](./javadoc/index.html)
- 📊 [Reporte de Cobertura (JaCoCo)](./coverage/index.html)
- 📐 [Diseño y Arquitectura (UML)](./DESIGN.md)
- 📝 [Historial de Cambios (Changelog)](./CHANGELOG.md)

## Resumen del Proyecto

Este proyecto es una implementación premium de Sudoku construida con **Java 21** y **JavaFX 21**. Se enfoca en una experiencia de usuario moderna con un núcleo técnico robusto.

### Características Técnicas
- **Validación en Tiempo Real**: Algoritmo optimizado para detección de conflictos.
- **Generación Procedural**: Creación de tableros con solución única.
- **Audio Multicanal**: Gestión de música y efectos mediante el patrón Singleton.
- **UI Responsiva**: Layouts dinámicos y estilos CSS personalizados.

## Pipeline de Integración Continua

Cada cambio en la rama `develop` ejecuta:
1. **Compilación**: Verificación de integridad del código.
2. **Tests**: Ejecución de JUnit 5.
3. **Calidad**: Análisis de cobertura (mínimo 80%).
4. **Docs**: Generación de este portal de documentación.
