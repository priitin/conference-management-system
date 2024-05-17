FROM maven:3.9-eclipse-temurin-22-alpine AS build
WORKDIR /app

COPY . .

RUN mvn install -pl web -am -DskipTests

FROM eclipse-temurin:22-jdk-alpine as final
WORKDIR /app

COPY --from=build /app/web/target/*.jar webapp.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "webapp.jar"]
