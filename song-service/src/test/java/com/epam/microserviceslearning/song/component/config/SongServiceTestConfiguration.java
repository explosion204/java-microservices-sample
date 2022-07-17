package com.epam.microserviceslearning.song.component.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.epam.microserviceslearning.song" })
@TestPropertySource(value = "classpath:/application.yaml")
public class SongServiceTestConfiguration {

}
