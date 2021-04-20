#!/bin/bash

ENVIRONMENT=""

build_application()
{
  gradle clean
  gradle build
}

deployment_dev()
{
  build_application
  gradle bootRun --args='--spring.profiles.active=dev'
}

deployment_local()
{
  build_application
  docker run -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=startwars -d -p 5432:5432 postgres:9.6
  docker build -t alfaville/starwar .
  docker run -p 9000:9000 alfaville/starwar
}

deployment_prod()
{
  build_application
  gradle bootBuildImage
  docker-compose pull
  docker-compose up -d
  docker-compose ps
}

environment_request()
{
  echo "In which environment do you want to run? "
  echo "DEV | LOCAL | PROD"
  read ENVIRONMENT
}

while [ "$ENVIRONMENT" == "" ]
do
  environment_request
  if [ "$ENVIRONMENT" == "DEV" ]; then
    deployment_dev
  elif [ "$ENVIRONMENT" == "LOCAL" ]; then
    deployment_local
  elif [ "$ENVIRONMENT" == "PROD" ]; then
    deployment_prod
  else
    echo "Invalid environment. Try again!"
    ENVIRONMENT=""
  fi
done

echo "Running in the $ENVIRONMENT environment"
