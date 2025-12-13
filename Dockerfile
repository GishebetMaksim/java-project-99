FROM gradle:8.7-jdk21 AS build

WORKDIR /app
COPY . .

WORKDIR /app
RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
