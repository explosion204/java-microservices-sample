package com.epam.microserviceslearning.resource.config;

import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.common.web.converter.CaseInsensitiveEnumConverter;
import com.google.gson.Gson;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ResourceServiceConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        final List<Class<? extends Enum>> enums = List.of(StorageType.class);

        enums.forEach(enumClass -> registry.addConverter(String.class, enumClass,
                new CaseInsensitiveEnumConverter<>(enumClass)));
    }

    @Bean
    public Tika tika() {
        return new Tika();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
