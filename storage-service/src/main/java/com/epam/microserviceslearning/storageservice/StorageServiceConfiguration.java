package com.epam.microserviceslearning.storageservice;

import com.epam.microserviceslearning.common.logging.trace.WebMvcTraceInterceptor;
import com.epam.microserviceslearning.common.storage.factory.StorageProvider;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.common.web.converter.CaseInsensitiveEnumConverter;
import com.epam.microserviceslearning.storageservice.security.client.GithubApiFeignClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class StorageServiceConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        final List<Class<? extends Enum>> enums = List.of(StorageType.class, StorageProvider.class);

        enums.forEach(enumClass -> registry.addConverter(String.class, enumClass,
                new CaseInsensitiveEnumConverter<>(enumClass)));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebMvcTraceInterceptor());
    }

    @Bean
    public GithubApiFeignClient githubApiFeignClient() {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .target(GithubApiFeignClient.class, "https://api.github.com");
    }
}
