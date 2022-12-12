#!/usr/bin/sh

cd /build

rm -f /build/default.iprof

echo "Starting Profiler..."
/build/target/springboot-benchmark &

echo "Waiting for Profiler to start..."
/build/wait-for.sh http://localhost:8080 -t 10

echo "Profiler started."

echo "Getting benchmarks..."
curl http://localhost:8080/benchmark

echo "Stopping Profiler..."
curl http://localhost:8080/shutdown

echo "Profiling complete"

# wait for profile info to be collected
while [ ! -f /build/default.iprof ]; do sleep 1; done
