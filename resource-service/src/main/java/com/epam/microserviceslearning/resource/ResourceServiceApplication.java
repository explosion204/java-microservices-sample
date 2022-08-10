package com.epam.microserviceslearning.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCaching
@ComponentScan(basePackages = {
        "com.epam.microserviceslearning.resource",
        "com.epam.microserviceslearning.common"
})
public class ResourceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServiceApplication.class, args);
    }
}
