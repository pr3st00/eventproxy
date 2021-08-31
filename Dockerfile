FROM openjdk:8-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# You might want to remove this for a production environment
ENV JAVA_OPTIONS=-XX:+UseSerialGC

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT java $JAVA_OPTIONS -jar /app.jar

# EOF
