FROM arm64v8/eclipse-temurin:17-jre

RUN addgroup --system spring && adduser --system spring && adduser spring spring
USER spring:spring

# You might want to remove this for a production environment
ENV JAVA_OPTIONS=-XX:+UseSerialGC

# For web security
ENV SPRING_USERNAME=admin
ENV SPRING_PASSWORD=password

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT java $JAVA_OPTIONS -jar /app.jar

# EOF
