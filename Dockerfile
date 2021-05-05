FROM gradle:6-jre11-openj9 AS TEMP_BUILD_IMAGE

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#COPY build.gradle settings.gradle $APP_HOME
#
#COPY gradle $APP_HOME/gradle
#COPY --chown=gradle:gradle . /home/gradle/src
#RUN chown -R gradle /home/gradle/src
#
#RUN gradle build || return 0
#COPY . .
#RUN gradle clean build

FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.10_9_openj9-0.24.0-alpine
LABEL maintainer="Felipe Alfaville"
LABEL version="0.2.0"

#WORKDIR $APP_HOME
#COPY --from=TEMP_BUILD_IMAGE build/libs/*.jar .

ARG JAR_FILE=build/libs/*.jar
COPY --from=TEMP_BUILD_IMAGE $JAR_FILE /opt/app/starwars.jar
#
#WORKDIR /app
#RUN mkdir /opt/app
#COPY ${JAR_FILE} /opt/app/starwars.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "/opt/app/starwars.jar"]

ENV PORT 9000
EXPOSE $PORT