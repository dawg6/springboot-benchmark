#!/bin/sh

images="openjdk graalvm native"
#images="openjdk graalvm native native-ee native-ee-pgo"

mkdir -p ./results
rm -rf ./results/*.txt

for f in $images
do
    echo "Waiting for $f to start..."
    host="http://$f:8080"
    echo "checking $host"
    ./wait-for.sh $host -t 10
    echo "$f has started."
done

sleep 5
echo "Starting tests..."

for f in $images
do
    echo "benchmarking $f..."
    echo "Results for $f" > ./results/$f.txt
    host="http://$f:8080"
    curl $host/benchmark | tee -a ./results/$f.txt
    echo "" >> ./results/$f.txt
done

echo "done"

cat ./results/*.txt

