#!/bin/sh
./mvnw package
docker-compose build  --progress=plain
