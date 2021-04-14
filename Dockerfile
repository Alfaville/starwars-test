FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.10_9_openj9-0.24.0-alpine
LABEL maintainer="lipealfaville@gmail.com"
LABEL version="0.2.0"

ARG JAR_FILE=build/libs/*.jar

WORKDIR /app
RUN mkdir /opt/app
COPY ${JAR_FILE} /opt/app/starwars.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/opt/app/starwars.jar"]

EXPOSE 9000

#https://spring.io/guides/topicals/spring-boot-docker
#docker build -t starwars-docker .
#docker build --build-arg JAR_FILE=build/libs/\*.jar -t starwars-docker .
#docker run -p 9000:9000 myorg/myapp
