package com.group15.jacksonpollock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fortress.security", "com.group15.jacksonpollock"})
@EnableMongoRepositories(basePackages ={"com.fortress.security", "com.group15.jacksonpollock"})
public class JacksonPollockApplication {

    public static void main(String[] args) {
        SpringApplication.run(JacksonPollockApplication.class, args);
    }

}
