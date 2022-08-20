package com.epam.microserviceslearning.resource.integration.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.epam.microserviceslearning.resource.persistence.db",
        "com.epam.microserviceslearning.resource.persistence.model"
})
@EntityScan("com.epam.microserviceslearning.resource.persistence.model")
@EnableJpaRepositories("com.epam.microserviceslearning.resource.persistence.db")
public class BinaryObjectRepositoryTestConfiguration {

}
