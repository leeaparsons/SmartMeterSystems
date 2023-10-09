package com.group15.interactivemapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fortress.security", "com.group15.interactivemapservice"})
@EnableMongoRepositories(basePackages ={"com.fortress.security", "com.group15.interactivemapservice"})
public class InteractiveMapServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InteractiveMapServiceApplication.class, args);
    }

}
