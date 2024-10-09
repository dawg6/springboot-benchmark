# SpringBoot Benchmark

This is a small project to do some quick benchmarks using Spring Boot in different container environments.

## Environments

- OpenJDK (using eclipse-temurin:21-jre-ubi9-minimal)
- Bellsoft (using bellsoft/liberica-runtime-container:jdk-21-stream-musl)
- GraalVM CE (using ghcr.io/graalvm/jdk-community:21)
- GraalVM CE Native (using ghcr.io/graalvm/native-image-community:21-ol9)

Note: the EE environments below have not yet been updated by Oracle to JDK 21 and the tests have been disabled

- GraalVM EE Natve (using container-registry.oracle.com/graalvm/native-image:21)
- GraalVM EE Natve PGO (using container-registry.oracle.com/graalvm/native-image:21 and Profile Guided Optimizations)

Each environment has been built into an image and published on https://hub.docker.com/r/dawg6/benchmark

- OpenJDK: dawg6/benchmark:openjdk-(version)
- Bellsoft: dawg6/benchmark:bellsoft-(version)
- GraalVM CE: dawg6/benchmark:graalvm-(version)
- GraalVM CE Native: dawg6/benchmark:native-(version)
- Note: Oracle GraalVM EE license prevents me from publishing the GraalVM EE images to hub.docker.com.

Also, there is a separate image to run the benchmark tests against all of the environments, using docker compose

- dawg6/benchmark:runner-(version)

## Benchmarks

- toJson (convert POJO object to Json)
- fromJson (convert Json to POJO)
- compute (perform CPU intensive operations - each iteration is 100 x several math computations)
- collection (create a Hashtable, populate it, iterate through it and remove each item)
- memCopy (create a 1MB byte array and copy it to another byte array)

Each benchmark is run 100,000 iterations

## Getting Started

Download the source code and create the .env file using env.example.txt as a template. The environment variables should be set to:

DOCKER_USER - The user to pull from docker (use dawg6 to pull my images)
IMAGE_TAG - The version of the below images to pull (default is latest)

The below are Port #s to expose on host machine if you want to query the image(s) yourself. Each must have a unique, unused port

- GRAALVM_PORT 
- NATIVE_PORT 
- OPENJDK_PORT
- BELLSOFT_PORT

### Building and running locally

The project is built using maven. You must have a version of Java 17 JDK installed locally and JAVA_HOME set.

    mvnw package

To run:

    mvnw springboot:run

The application will listen on port 8080

The following endpoints are supported:

- / - Returns "up" to indicate the image is running
- /bean - Returns Json bean used for to/from Json benchmarks
- /benchmark - Runs all benchmarks and returns combined results with a total
- /benchmarkToJson - To Json Benchmark
- /benchmarkFromJson - From Json Benchmark
- /benchmarkCompute - Compute Benchmark
- /benchmarkMemCopy - Mem Copy Benchmark
- /benchmarkCollection - Collection Benchmark
- /shutdown - Shutsdown the app. This is mainly used for profiling for the native-ee-pgo image

### Building using docker compose (or docker-compose)

**Note: In order to run the GraalVM Enterprise Edition tests, you will need to download and build using the GraalVM EE images. Instructions for doing this are in the file [README-GraalVM-EE.md](README-GraalVM-EE.md).**

The project is built and run using docker compose on windows or docker-compose on linux.

    docker compose build

Note: Add "--progress=plain" to see raw build output.

The scripts [build_all.bat](build_all.bat) and [build_all.sh](build_all.sh) have been provided as a convenience.

If you don't want to build, and just want to run, you can pull images from the DOCKER_USER repo.

    docker compose pull

To run

    docker compose up --abort-on-container-exit

This will start up each image, run the benchmarks and then exit. Results will be written to the subdirectory ./results (one .txt file for each image)

The scripts [run_all.bat](run_all.bat) and [run_all.sh](run_all.sh) have been provided as a convenience.

## Results Format

The results are written as a Json file:

    [
        {
            name: Name of the benchmark
            elapsedMs: # of milliseconds to run the benchmark
            iterations: # of iterations
            iterationsPerMs: # of iterations per millisecond
        },
        ...
    ]

# My Results

My results are in the sub-folder [latest-results](latest-results/)
