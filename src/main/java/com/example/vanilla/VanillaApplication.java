package com.example.vanilla;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ServletComponentScan
// @RegisterReflectionForBinding(classes = {
// 	BenchmarkBean.class
// })
public class VanillaApplication {

	private static final Logger log = LoggerFactory.getLogger(VanillaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(VanillaApplication.class, args);
		log.info("Application Started");
	}

	@PostConstruct
    public void init() {
        log.info("Init complete");
    }
}
