package com.optimization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.optimization", "com.optimization.controller", "com.optimization.service", "com.optimization.model"})
public class OptimizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(OptimizationApplication.class, args);
    }
} 