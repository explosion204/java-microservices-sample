package com.epam.microserviceslearning.processor.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfiguration {
    @Value("${resource-service.url}")
    private String resourceServiceUrl;

    @Value("${song-service.url}")
    private String songServiceUrl;

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Qualifier("resource-service")
    public WireMockServer resourceServiceMock() {
        final int port = getPort(resourceServiceUrl);
        return new WireMockServer(port);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Qualifier("song-service")
    public WireMockServer songServiceMock() {
        final int port = getPort(songServiceUrl);
        return new WireMockServer(port);
    }

    private int getPort(String url) {
        return Integer.parseInt(StringUtils.substringAfterLast(url, ":"));
    }
}
