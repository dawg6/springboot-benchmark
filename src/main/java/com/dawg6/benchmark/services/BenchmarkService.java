package com.dawg6.benchmark.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawg6.benchmark.data.BenchmarkBean;
import com.dawg6.benchmark.data.BenchmarkResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class BenchmarkService {
    private static final Logger log = LoggerFactory.getLogger(BenchmarkService.class);

    @Autowired
    BenchmarkBean bean;

    @GetMapping("/")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("up");
    }

    @GetMapping("/bean")
    public ResponseEntity<BenchmarkBean> getBean() {
        return ResponseEntity.ok(bean);
    }

    @GetMapping("/benchmarkToJson")
    public ResponseEntity<BenchmarkResult> benchmarkToJson() throws Exception {
        var count = 100_000;
        var mapper = new ObjectMapper();

        var start = System.currentTimeMillis();
        for (var i = 0; i < count; i++) {
            mapper.writeValueAsString(bean);
        }

        var elapsed = System.currentTimeMillis() - start;

        return ResponseEntity.ok(new BenchmarkResult("toJson", elapsed, count));
    }

    @GetMapping("/benchmarkFromJson")
    public ResponseEntity<BenchmarkResult> benchmarkFromJson() throws Exception {
        var count = 100_000;
        var mapper = new ObjectMapper();
        var text = mapper.writeValueAsString(bean);
            
        var start = System.currentTimeMillis();
        for (var i = 0; i < count; i++) {
            mapper.readValue(text, BenchmarkBean.class);
        }

        var elapsed = System.currentTimeMillis() - start;

        return ResponseEntity.ok(new BenchmarkResult("fromJson", elapsed, count));
    }

    @GetMapping("/benchmarkCompute")
    public ResponseEntity<BenchmarkResult> benchmarkCompute() throws Exception {
        var count = 100_000;
        var r = new Random(420);
        var value = r.nextDouble(Math.PI);

        var start = System.currentTimeMillis();
        for (var i = 0; i < count * 100; i++) {
            value = Math.atan(Math.tan(value));
        }
        var elapsed = System.currentTimeMillis() - start;

        return ResponseEntity.ok(new BenchmarkResult("compute", elapsed, count));
    }

    @GetMapping("/benchmark")
    public ResponseEntity<List<BenchmarkResult>> benchmarkAll() throws Exception {
        var list = new ArrayList<BenchmarkResult>(10);
        list.add(benchmarkToJson().getBody());
        list.add(benchmarkFromJson().getBody());
        list.add(benchmarkCompute().getBody());

        var total = list.stream().reduce(new BenchmarkResult("total"), (i, e) -> i.combine(e));
        list.add(total);

        return ResponseEntity.ok(list);
    }

    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception e) {
        log.error("Exception", e);
    }
    
}
