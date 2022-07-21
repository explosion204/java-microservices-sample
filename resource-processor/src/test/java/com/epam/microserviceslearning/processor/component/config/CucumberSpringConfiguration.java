package com.epam.microserviceslearning.processor.component.config;

import com.epam.microserviceslearning.processor.utils.WireMockConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = { ResourceProcessorTestConfiguration.class, WireMockConfiguration.class }
)
@CucumberContextConfiguration
@DirtiesContext
public class CucumberSpringConfiguration {

}
