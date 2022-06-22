package com.epam.microserviceslearning.processor;

import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceProcessorConfiguration {
    @Bean
    public Mp3Parser mp3Parser() {
        return new Mp3Parser();
    }
}
