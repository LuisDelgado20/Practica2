# Etapa 1: Construcción (Build)
# Usamos Maven para compilar el proyecto dentro de Render
FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
# Usamos una imagen ligera para correr el .jar generado
FROM amazoncorretto:17-alpine-jdk
# Copiamos el archivo generado en la etapa anterior
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]