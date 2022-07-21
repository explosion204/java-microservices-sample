package com.epam.microserviceslearning.song;

import com.epam.microserviceslearning.common.csv.CsvService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SongServiceConfiguration {
    @Bean
    public CsvService csvService() {
        return new CsvService();
    }
}
