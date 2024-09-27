package org.example.config;

import org.example.function.Method1;
import org.example.function.Method2;
import org.example.service.DatabaseQueryServicve;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Sample {

    @Bean
    Method1 method1() {
        return new Method1();
    }

    @Bean
    Method2 method2(DatabaseQueryServicve databaseQueryService) {
        return new Method2(databaseQueryService);
    }
}