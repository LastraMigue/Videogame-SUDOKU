# Documentación Técnica — Sudoku JavaFX

Bienvenido a la documentación técnica del proyecto Sudoku JavaFX. Este sitio se genera automáticamente mediante **GitHub Actions** en cada integración a la rama `develop`.

## Enlaces Rápidos

- 📚 [Referencia API Javadoc](./javadoc/index.html)
- 📊 [Reporte de Cobertura de Código](./coverage/index.html)
- 📐 [Diagramas UML (Mermaid)](./uml.md)
- 📝 [Historial de Cambios](../CHANGELOG.md)

## Resumen del Proyecto

Este proyecto implementa un juego de Sudoku completo utilizando **Java 21**, **JavaFX 21** y **Maven**. La arquitectura sigue el patrón MVC para asegurar una separación clara entre la lógica de negocio, la interfaz de usuario y los servicios de soporte.

## Pipeline CI/CD

Cada push a la rama `develop` dispara:
1. **Validación de Tests**: Ejecución de la suite JUnit 5.
2. **Análisis de Cobertura**: Verificación de que al menos el 80% del código está probado.
3. **Generación de Javadoc**: Extracción de documentación técnica del código.
4. **Despliegue automático**: Actualización de este sitio web.
