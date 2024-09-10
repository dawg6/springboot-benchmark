# Downloading and building Oracle GraalVM Enterprise Edition native image

Note: The Oracle GraalVM EE license agreement precludes me from publushing the benchmark images built on GraalVM EE to hub.docker.com, so you must build them locally.

1. Navigate to https://container-registry.oracle.com/.
2. Click GraalVM to browse the GraalVM containers
3. Click native-image-ee
4. Sign in or Register for an account
5. First time, you must accept the license agreement
6. First time you will also need to log in from the command line:
7. CLick the breadcrumb link for GraalVM Repositories to go back to the image list.
8. Click enterprise
9. First time, you must accept the license agreement

10. You can now build the GraalVM EE images locally that you will need to run the tests by running the commands below (use docker-compose for linux).

    docker compose build native-ee
    docker compose build native-ee-gpo

Alternatively for step 8, you can build all of the images locally, using the instructions in [README.md](README.md)
