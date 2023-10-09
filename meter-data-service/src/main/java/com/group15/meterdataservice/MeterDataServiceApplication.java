package com.group15.meterdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fortress.security", "com.group15.meterdataservice"})
@EnableMongoRepositories(basePackages ={"com.fortress.security", "com.group15.meterdataservice"})
public class MeterDataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeterDataServiceApplication.class, args);
    }

}
