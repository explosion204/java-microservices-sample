package com.epam.microserviceslearning.resource.integration.config;

import com.amazonaws.services.s3.AmazonS3;
import com.epam.microserviceslearning.common.storage.factory.StorageFactory;
import com.epam.microserviceslearning.resource.integration.mock.AmazonS3MockClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan("com.epam.microserviceslearning.resource.persistence.storage")
@Import(StorageFactory.class)
public class StorageServiceTestConfiguration {
    @Bean
    public AmazonS3 s3() {
        return new AmazonS3MockClient();
    }
}
