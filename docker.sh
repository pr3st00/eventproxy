#!/bin/bash

# --------------------------------------------------------
# Docker helper script
# Author: Fernando Costa de Almeida
# --------------------------------------------------------

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

function build() {
	echo "[INFO] Building container [${CONTAINER_NAME}]"
	docker build -t ${DOCKER_USER}/${CONTAINER_NAME} .
}

function run() {
	echo "[INFO] Running container [${CONTAINER_NAME}]"

	if [[ ! -z $NETWORK ]]; then
		echo "..Using network $NETWORK"
		network_param="--network=$NETWORK"
	fi

	if [[ ! -z $DNS ]]; then
		echo "..Using dns $DNS"
		dns_param="--dns $DNS"
	fi

	docker run --restart no -d -p ${SERVER_PORT}:${SERVER_PORT} \
         $network_param \
         $dns_param \
	 -e SERVER.PORT=${SERVER_PORT} \
	 -e SPRING.USERNAME=${SPRING_USERNAME} \
	 -e SPRING.PASSWORD=${SPRING_PASSWORD} \
	 --name $CONTAINER_NAME ${DOCKER_USER}/${CONTAINER_NAME}
}

function start() {
	echo "[INFO] Starting container [${CONTAINER_NAME}]"
	docker start $CONTAINER_NAME
}

function stop() {
	echo "[INFO] Stopping container [${CONTAINER_NAME}]"
	docker stop $CONTAINER_NAME
}

function status() {
	echo "[INFO] Running containers: "
	echo
	docker ps | grep $CONTAINER_NAME
}

function remove() {
	echo "[INFO] Removing container [${CONTAINER_NAME}]"
	docker rm $CONTAINER_NAME
}


case $1 in
	build)
		build 
		;;
	run)
		run
		;;
	remove)
		stop && remove
		;;
	start)
		start
		;;
	stop)
		stop
		;;
        status)
		status
		;;
        update)
		stop && remove && build && run && status
		;;
	*)
		echo "Usage: docker.sh {build|run|remove|start|stop|update|status}"
		exit 1
		;;
esac

exit 0

# EOF
