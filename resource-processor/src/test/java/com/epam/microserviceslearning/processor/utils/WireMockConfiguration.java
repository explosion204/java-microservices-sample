package com.epam.microserviceslearning.processor.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfiguration {
    @Value("${service-gateway.url}")
    private String serviceGatewayUrl;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer serviceGatewayMock() {
        final int port = getPort(serviceGatewayUrl);
        return new WireMockServer(port);
    }

    private int getPort(String url) {
        return Integer.parseInt(StringUtils.substringAfterLast(url, ":"));
    }
}
