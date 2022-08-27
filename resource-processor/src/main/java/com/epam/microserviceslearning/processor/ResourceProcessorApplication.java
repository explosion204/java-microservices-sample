package com.epam.microserviceslearning.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {
        "com.epam.microserviceslearning.processor",
        "com.epam.microserviceslearning.common.logging"
})
public class ResourceProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceProcessorApplication.class, args);
    }
}
