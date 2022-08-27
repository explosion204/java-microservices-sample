package com.epam.microserviceslearning.resource.component.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfiguration {
    @Value("${service-gateway.url}")
    private String gatewayUrl;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer resourceServiceMock() {
        final int port = getPort(gatewayUrl);
        return new WireMockServer(port);
    }

    private int getPort(String url) {
        return Integer.parseInt(StringUtils.substringAfterLast(url, ":"));
    }
}
