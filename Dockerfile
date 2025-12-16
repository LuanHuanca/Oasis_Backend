# Dockerfile para Oasis Backend
# Multi-stage build para optimizar el tamaño de la imagen

# Etapa 1: Build
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copiar archivos de configuración de Gradle
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Copiar código fuente
COPY src ./src

# Construir la aplicación con flag -parameters para Spring
RUN gradle clean build -x test --no-daemon -Porg.gradle.java.compile-classpath-packaging=true

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar el JAR desde la etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto
EXPOSE 9999

# Instalar curl para healthcheck
USER root
RUN apk add --no-cache curl
USER spring:spring

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:9999/actuator/health || exit 1

# Ejecutar la aplicación con perfil docker
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]

