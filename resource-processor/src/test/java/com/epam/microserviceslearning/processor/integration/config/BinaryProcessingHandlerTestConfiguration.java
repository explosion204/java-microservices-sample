package com.epam.microserviceslearning.processor.integration.config;

import com.epam.microserviceslearning.processor.ResourceProcessorConfiguration;
import com.epam.microserviceslearning.processor.client.ResourceServiceClient;
import com.epam.microserviceslearning.processor.client.SongServiceClient;
import com.epam.microserviceslearning.processor.utils.WireMockConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.epam.microserviceslearning.processor.messaging",
        "com.epam.microserviceslearning.processor.service",
        "com.epam.microserviceslearning.common.logging"
})
@EnableFeignClients(clients = { ResourceServiceClient.class, SongServiceClient.class })
@Import(value = { ResourceProcessorConfiguration.class, WireMockConfiguration.class })
@Profile("test")
public class BinaryProcessingHandlerTestConfiguration {

}
