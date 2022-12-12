package com.dawg6.benchmark.components;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.dawg6.benchmark.data.BenchmarkBean;

@Component
public class BenchmarkConfig {

    private BenchmarkBean bean;

    @Bean
    public BenchmarkBean getBenchmarkBean() {
        if (bean == null) {
            bean = new BenchmarkBean();
            bean.initialize();
        }

        return bean;
    }
}
