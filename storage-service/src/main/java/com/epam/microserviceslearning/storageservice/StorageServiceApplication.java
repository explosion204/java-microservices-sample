package com.epam.microserviceslearning.storageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan(basePackages = {
        "com.epam.microserviceslearning.storageservice",
        "com.epam.microserviceslearning.common.csv",
        "com.epam.microserviceslearning.common.logging",
})
public class StorageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }
}
