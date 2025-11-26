# Mutant Detector API

![Java 17](https://img.shields.io/badge/Java-17-blue) ![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen) ![Coverage >90%](https://img.shields.io/badge/Coverage-%3E90%25-success)

## DescripciИn
API REST para detectar mutantes a partir de secuencias de ADN (NxN) buscando mカltiples secuencias de cuatro bases iguales en direcciones horizontal, vertical y diagonales. Incluye persistencia, estadヴsticas y documentaciИn interactiva.

## Datos personales
- Nombre: Lucia Dominguez
- Legajo: 50881
- Comision: 3K10

## Instrucciones de EjecuciИn
- Ejecutar tests: `./gradlew test`
- Generar reporte de cobertura: `./gradlew test jacocoTestReport` (HTML en `build/reports/jacoco/test/html/index.html`)
- Iniciar la aplicaciИn: `./gradlew bootRun`

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
- Obtener estadヴsticas:
  ```bash
  curl -X GET http://localhost:8080/stats
  ```

## Tecnologヴas
- Java 17
- Spring Boot 3
- H2 Database
- Lombok
- JUnit 5
- Mockito
- JaCoCo
