package org.example;

import org.example.config.AppConfig;
import org.example.config.Sample;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

// @Import(AppConfig.class)
// @SpringBootConfiguration
// @EnableAutoConfiguration
@Import(Sample.class)
@SpringBootApplication
public class Main {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(Main.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}