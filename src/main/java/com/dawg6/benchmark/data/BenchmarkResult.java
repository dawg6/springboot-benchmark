package com.dawg6.benchmark.data;

public class BenchmarkResult {
    private String name;
    private long elapsedMs;
    private long iterations;
    private double iterationsPerMs;

    public BenchmarkResult() {}

    public BenchmarkResult(String name) {
        this.name = name;
        iterations = 0;
        elapsedMs = 0;
        iterationsPerMs = 0;
    }

    public BenchmarkResult(String name, long elapsedMs, long iterations) {
        this(name);
        this.elapsedMs = elapsedMs;
        this.iterations = iterations;
        if (elapsedMs > 0) {
            this.iterationsPerMs = (double)iterations / (double)elapsedMs;
        }
    }

    public long getElapsedMs() {
        return elapsedMs;
    }

    public void setElapsedMs(long elapsedMs) {
        this.elapsedMs = elapsedMs;
    }

    public long getIterations() {
        return iterations;
    }

    public void setIterations(long iterations) {
        this.iterations = iterations;
    }

    public double getIterationsPerMs() {
        return iterationsPerMs;
    }

    public void setIterationsPerMs(double iterationsPerMs) {
        this.iterationsPerMs = iterationsPerMs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BenchmarkResult combine(BenchmarkResult e) {
        iterations += e.iterations;
        elapsedMs += e.elapsedMs;
        if (elapsedMs > 0) {
            iterationsPerMs = (double)iterations / (double)elapsedMs;
        }

        return this;
    }
}
