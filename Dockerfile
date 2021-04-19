FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.10_9_openj9-0.24.0-alpine
LABEL maintainer="Felipe Alfaville"
LABEL version="0.2.0"

ARG JAR_FILE=build/libs/*.jar

WORKDIR /app
RUN mkdir /opt/app
COPY ${JAR_FILE} /opt/app/starwars.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/opt/app/starwars.jar"]

ENV PORT 9000
EXPOSE $PORT
