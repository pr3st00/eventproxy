#!/bin/sh

#
# Starts the eventproxy container
#

CONTAINER_NAME=eventproxy

# Change to match your configuration
DOCKER_USER=pr3st00
PORT=8081

docker run --restart always -d -p ${PORT}:${PORT} --name $CONTAINER_NAME ${DOCKER_USER}/eventproxy

# EOF
