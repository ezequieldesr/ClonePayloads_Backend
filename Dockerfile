FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline
COPY src ./src
RUN mvn -B -ntp verify
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd --system clonepayloads && useradd --system --gid clonepayloads clonepayloads
COPY --from=build --chown=clonepayloads:clonepayloads /app/target/*.jar app.jar
USER clonepayloads:clonepayloads
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
