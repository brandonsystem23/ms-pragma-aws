# --- ETAPA 1: Construcción (Build) ---
FROM gradle:8.10.2-jdk21 AS builder

WORKDIR /app

# 1. Copiamos solo los archivos de configuración de Gradle primero
# Esto permite que Docker guarde en caché las dependencias y no las descargue
# de nuevo a menos que cambies el build.gradle o settings.gradle.
COPY build.gradle settings.gradle ./
COPY gradle gradle

# Pre-descargamos las dependencias (se quedan en caché si no cambian los archivos de arriba)
RUN gradle dependencies --no-daemon

# 2. Ahora sí copiamos el resto del código fuente
COPY src src

# Compilamos el jar (omitiendo la tarea clean para aprovechar la caché de compilación si es posible)
RUN gradle bootJar --no-daemon


# --- ETAPA 2: Ejecución (Runtime) ---
FROM eclipse-temurin:21-jre-alpine AS runner

WORKDIR /app

# Copiamos el jar generado desde la etapa de builder
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]