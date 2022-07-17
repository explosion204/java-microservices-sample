package com.epam.microserviceslearning.song.integration.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.epam.microserviceslearning.song.persistence")
@Profile("test")
public class SongMetadataRepositoryTestConfiguration {

}
