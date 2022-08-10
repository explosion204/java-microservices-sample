package com.epam.microserviceslearning.storageservice;

import com.epam.microserviceslearning.common.csv.CsvService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CsvService.class)
@EnableEurekaClient
public class StorageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }
}
