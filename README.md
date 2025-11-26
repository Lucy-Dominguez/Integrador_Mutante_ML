# Mutant Detector API

![Java 17](https://img.shields.io/badge/Java-17-blue) ![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen) ![Coverage >90%](https://img.shields.io/badge/Coverage-%3E90%25-success)

## Descripción
API REST para detectar mutantes a partir de secuencias de ADN (NxN) buscando múltiples secuencias de cuatro bases iguales en direcciones horizontal, vertical y diagonales. Incluye persistencia, estadísticas y documentación interactiva.

## Instrucciones de Ejecución
- Ejecutar tests: `./gradlew test`
- Generar reporte de cobertura: `./gradlew test jacocoTestReport` (HTML en `build/reports/jacoco/test/html/index.html`)
- Iniciar la aplicación: `./gradlew bootRun`

## Uso de la API
- URL local: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Consola H2: `http://localhost:8080/h2-console`

### Ejemplos cURL
- Detectar mutante:
  ```bash
  curl -X POST http://localhost:8080/mutant \
    -H "Content-Type: application/json" \
    -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'
  ```
- Obtener estadísticas:
  ```bash
  curl -X GET http://localhost:8080/stats
  ```

## Tecnologías
- Java 17
- Spring Boot 3
- H2 Database
- Lombok
- JUnit 5
- Mockito
- JaCoCo
