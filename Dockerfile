FROM gradle:8.7-jdk21 AS build

WORKDIR /app
COPY . .

WORKDIR /app/demo
RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/demo/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
