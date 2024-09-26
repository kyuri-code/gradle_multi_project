package org.example.config;

import org.apache.logging.log4j.util.Supplier;
import org.example.function.Method1;
import org.example.function.Method2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class Sample {

    private final Logger logger = LoggerFactory.getLogger(Sample.class);
    
    // @Bean
    // Method1 method1() {
    //     return new Method1();
    // }

    // @Bean
    // Method2 method2() {
    //     return new Method2();
    // }

    // @Bean
    public Supplier<Void> method1() {
        return () -> {
            logger.info("hello world from method1");
            return null;
        };
    }

    // @Bean
    public Supplier<Void> method2() {
        return () -> {
            logger.info("hello world from method2");
            return null;
        };
    }
}