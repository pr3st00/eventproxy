#!/bin/bash

#
# Runs the eventproxy container
#

CONTAINER_NAME=eventproxy

# Defaults. Values from .env file can overwrite those
DOCKER_USER=pr3st00
SERVER_PORT=8081
SPRING_USERNAME=admin
SPRING_PASSWORD=password

if [[ -f .env ]]; then
	echo "Loading variables from .env file"
	. ./.env
fi

docker run --restart always -d -p ${SERVER_PORT}:${SERVER_PORT} \
  -e SERVER.PORT=${SERVER_PORT} \
  -e SPRING.USERNAME=${SPRING_USERNAME} \
  -e SPRING.PASSWORD=${SPRING_PASSWORD} \
  --name $CONTAINER_NAME ${DOCKER_USER}/${CONTAINER_NAME}

# EOF
