package com.epam.microserviceslearning.song.component.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SongServiceTestConfiguration.class
)
@CucumberContextConfiguration
@DirtiesContext
public class CucumberSpringConfiguration {

}
