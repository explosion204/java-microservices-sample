package com.epam.microserviceslearning.resource.config;

import com.epam.microserviceslearning.common.csv.CsvService;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceServiceConfiguration {
    @Bean
    public Tika tika() {
        return new Tika();
    }

    @Bean
    public CsvService csvService() {
        return new CsvService();
    }
}
