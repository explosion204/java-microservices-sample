package com.epam.microserviceslearning.resource.component.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ResourceServiceTestConfiguration.class
)
@CucumberContextConfiguration
@DirtiesContext
public class CucumberSpringConfiguration {

}
