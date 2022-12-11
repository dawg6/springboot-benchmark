# SpringBoot Benchmark

This is a small project to do some quick benchmarks using Spring Boot in different container environments.

## Environments

- OpenJDK (using eclipse-temurin:17-jre-ubi9-minimal)
- GraalVM (using ghcr.io/graalvm/jdk:ol9-java17-22.3.0-b2)
- Native (using ghcr.io/graalvm/native-image:ol9-java17)

## Benchmarks

- toJson (convert POJO object to Json)
- fromJson (convert Json to POJO)
- compute (perform CPU intensive operations)

Each benchmark is run 100,000 times

## Getting Started

Download the source code and create the .env file using env.example.txt as a template. The environment variables should be set to:

DOCKER_USER - The user to pull from docker (use dawg6 to pull my images)

The below are Port #s to expose on host machine if you want to query the image(s) yourself. Each must have a unique, unused port

- GRAALVM_PORT
- NATIVE_PORT
- OPENJDK_PORT

### Building and running locally

The project is built using maven. 

    ./mvnw package

To run:

    ./mvnw springboot:run

The application will listen on port 8080

The following endpoints are supported:

- / - Returns "up" to indicate the image is running
- /bean - Returns Json bean used for to/from Json benchmarks
- /benchmark - Runs all benchmarks and returns combined results with a total
- /benchmarkToJson - To Json Benchmark
- /benchmarkFromJson - From Json Benchmark
- /benchmarkCompute - Compute Benchmark

### Building using docker compose (or docker-compose)

The project is built and run using docker compose on windows or docker-compose on linux.

    docker compose build

If you don't want to build, and just want to run, you can pull images:

    docker compose pull

To run

    docker compose up --abort-on-container-exit

This will start up each image, run the benchmarks and then exit. Results will be written to the subdirectory ./results (one .txt file for each image)


## Results Format

The results are written as a Json file:

    {
        name: Name of the benchmark
        elapsedMs: # of milliseconds to run the benchmark
        iterations: # of iterations
        iterationsPerMs: # of iterations per millisecond
    }

