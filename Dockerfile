FROM adoptopenjdk:11-jre-openj9
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} starwars.jar
ENTRYPOINT ["java","-jar","/starwars.jar"]


#FROM node AS base
#
## Dockerize is needed to sync containers startup
#ENV DOCKERIZE_VERSION v0.6.0
#RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
#    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
#    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz
#
#RUN mkdir -p ~/app
#
#WORKDIR ~/app
#
#COPY package.json .
#COPY yarn.lock .
#
#FROM base AS dependencies
#
#RUN yarn
#
#FROM dependencies AS runtime
#
#COPY . .