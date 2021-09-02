#!/bin/sh

#
# Runs the eventproxy container
#

CONTAINER_NAME=eventproxy

# Defaults. Values from .env file can overwrite those
DOCKER_USER=pr3st00
SERVER.PORT=8081
SPRING.USERNAME=admin
SPRING.PASSWORD=password

if [[ -f .env ]]; then
	echo "Loading variables from .env file"
	. ./.env
]]

docker run --restart always -d -p ${SERVER.PORT}:${SERVER.PORT} \
  -e SERVER.PORT=${SERVER.PORT} \
  -e SPRING.USERNAME=${SPRING.USERNAME} \
  -e SPRING.PASSWORD=${SPRING.PASSWORD} \
  --name $CONTAINER_NAME ${DOCKER_USER}/${CONTAINER_NAME}

# EOF
