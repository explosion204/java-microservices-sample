package com.epam.microserviceslearning.common.testutils;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStream;

public class TestUtils {
    @SneakyThrows
    public static InputStream loadFileFromResources(String name) {
        final Resource resource = new ClassPathResource(name);
        return resource.getInputStream();
    }
}
