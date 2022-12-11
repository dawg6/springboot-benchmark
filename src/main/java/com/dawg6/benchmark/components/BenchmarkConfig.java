package com.dawg6.benchmark.components;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.dawg6.benchmark.data.BenchmarkBean;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BenchmarkConfig {
    private static final Logger log = LoggerFactory.getLogger(BenchmarkConfig.class);

    private BenchmarkBean bean;

    @Bean
    public BenchmarkBean getBenchmarkBean() {
        if (bean == null) {
            try {
                var rand = new Random(420);
                var mapper = new ObjectMapper();
                bean = new BenchmarkBean();
                bean.setaNumber(rand.nextInt(Integer.MAX_VALUE));
                bean.setaString(UUID.randomUUID().toString());
                var list = new String[100];
                for (var n = 0; n < list.length; n++) {
                    list[n] = UUID.randomUUID().toString();
                }
                bean.setaList(list);
                    mapper.writeValueAsString(bean);
            }
            catch (Exception e) {
                log.error("Error creating bean", e);
            }
        }

        return bean;
    }
}
