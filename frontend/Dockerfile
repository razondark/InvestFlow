FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -N install

COPY /frontend/. .

RUN mvn vaadin:build-frontend

RUN mvn install -DskipTests

FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/*.war app.war

CMD ["java", "-jar", "app.war"]