# Stage 1: build the WAR using Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

# Stage 2: runtime image — Tomcat + H2 server, both started via entrypoint.sh
FROM tomcat:9.0-jdk17-temurin

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Deploy as ROOT so the app is served at "/" instead of "/sriram-mart"
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/sriram-mart.war /usr/local/tomcat/webapps/ROOT.war

# Standalone H2 jar for running the server process (must match pom.xml's h2.version)
RUN curl -sSL -o /opt/h2.jar https://repo1.maven.org/maven2/com/h2database/h2/2.3.232/h2-2.3.232.jar

RUN mkdir -p /opt/srirammart/data

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/entrypoint.sh"]