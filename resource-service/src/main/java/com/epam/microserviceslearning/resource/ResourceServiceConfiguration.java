package com.epam.microserviceslearning.resource;

import com.epam.microserviceslearning.common.logging.trace.FeignTraceInterceptor;
import com.epam.microserviceslearning.common.logging.trace.WebMvcTraceInterceptor;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.common.web.converter.CaseInsensitiveEnumConverter;
import com.google.gson.Gson;
import feign.RequestInterceptor;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebMvcTraceInterceptor());
    }

    @Bean
    public RequestInterceptor feignTraceInterceptor() {
        return new FeignTraceInterceptor();
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
