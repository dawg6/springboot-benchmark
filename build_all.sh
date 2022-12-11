#!/bin/sh
./mvnw package
docker-compose build
