package com.epam.microserviceslearning.storageservice;

import com.epam.microserviceslearning.common.storage.factory.StorageProvider;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.common.web.converter.CaseInsensitiveEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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
}
