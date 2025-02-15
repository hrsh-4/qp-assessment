package com.grocery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GroceryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroceryServiceApplication.class, args);
    }
}
