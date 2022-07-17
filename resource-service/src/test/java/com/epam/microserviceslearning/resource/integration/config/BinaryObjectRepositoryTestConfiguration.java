package com.epam.microserviceslearning.resource.integration.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.epam.microserviceslearning.resource.persistence.db",
        "com.epam.microserviceslearning.resource.persistence.model"
})
public class BinaryObjectRepositoryTestConfiguration {

}
