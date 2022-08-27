package com.epam.microserviceslearning.song;

import com.epam.microserviceslearning.common.csv.CsvService;
import com.epam.microserviceslearning.common.logging.trace.WebMvcTraceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SongServiceConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebMvcTraceInterceptor());
    }

    @Bean
    public CsvService csvService() {
        return new CsvService();
    }
}
