# Usar JDK 17 ligero
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Asegurar permisos de gradlew
RUN chmod +x ./gradlew

# Construir el JAR sin tests
RUN ./gradlew bootJar -x test

# Puerto de la aplicación
EXPOSE 8080

# Ejecutar el JAR generado (nombre derivado de settings.gradle)
CMD ["java", "-jar", "build/libs/Integrador_Mutante-0.0.1-SNAPSHOT.jar"]
