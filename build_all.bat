./mvnw package
docker compose -f docker-compose-openjdk.yml build
docker compose -f docker-compose-graalvm.yml build
docker compose -f docker-compose-native.yml build
