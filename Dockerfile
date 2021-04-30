FROM ghcr.io/graalvm/graalvm-ce:21.1.0
LABEL maintainer="Felipe Alfaville"
LABEL version="0.2.0"

ARG JAR_FILE=build/libs/*.jar

WORKDIR /app
RUN mkdir /opt/app
COPY ${JAR_FILE} /opt/app/starwars.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "/opt/app/starwars.jar"]

ENV PORT 9000
EXPOSE $PORT