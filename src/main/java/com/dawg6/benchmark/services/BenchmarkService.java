package com.dawg6.benchmark.services;

import java.util.ArrayList;
import java.util.Hashtable;
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

    private static final long count = 100_000l;

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
        var r = new Random(420);

        var start = System.currentTimeMillis();
        for (var i = 0; i < count * 100; i++) {
            var value = r.nextDouble(Math.PI);
            var value2 = r.nextLong();
            var value3 = r.nextInt();
            value = Math.atan(Math.tan(value)) * Math.sqrt(value) * Math.pow(value, value3) * (value3 * value3) - value2;
        }
        var elapsed = System.currentTimeMillis() - start;

        return ResponseEntity.ok(new BenchmarkResult("compute", elapsed, count));
    }

    @GetMapping("/benchmarkCollection")
    public ResponseEntity<BenchmarkResult> benchmarkCollection() throws Exception {
        var map = new Hashtable<Long, Double>();
        var r = new Random();

        var start = System.currentTimeMillis();
        for (var i = 0; i < count * 100; i++) {
            map.put(r.nextLong(), r.nextDouble());
        }
        for (var i = map.entrySet().iterator(); i.hasNext(); ) {
            i.next();
            i.remove();
        }

        var elapsed = System.currentTimeMillis() - start;

        return ResponseEntity.ok(new BenchmarkResult("collection", elapsed, count));
    }

    @GetMapping("/benchmarkMemCopy")
    public ResponseEntity<BenchmarkResult> benchmarkMemCopy() throws Exception {
        var count = 100_000;
        var array1 = new byte[1*1024*1024];
        var array2 = new byte[array1.length];
        var r = new Random(420);
        r.nextBytes(array1);

        var start = System.currentTimeMillis();
        for (var i = 0; i < count; i++) {
            System.arraycopy(array1, 0, array2, 0, array1.length);
        }
        var elapsed = System.currentTimeMillis() - start;

        return ResponseEntity.ok(new BenchmarkResult("memCopy", elapsed, count));
    }

    @GetMapping("/shutdown")
    public ResponseEntity<String> shutdown() {
        log.info("Shutting down");
        new Thread(new Runnable() {

            @Override
            public void run() {
                Thread.yield();
                System.exit(0);
            }
        }).start();

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/benchmark")
    public ResponseEntity<List<BenchmarkResult>> benchmarkAll() throws Exception {
        var list = new ArrayList<BenchmarkResult>(10);
        list.add(benchmarkToJson().getBody());
        list.add(benchmarkFromJson().getBody());
        list.add(benchmarkCompute().getBody());
        list.add(benchmarkMemCopy().getBody());
        list.add(benchmarkCollection().getBody());

        var total = list.stream().reduce(new BenchmarkResult("total"), (i, e) -> i.combine(e));
        list.add(0, total);

        return ResponseEntity.ok(list);
    }

    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception e) {
        log.error("Exception", e);
    }

}
