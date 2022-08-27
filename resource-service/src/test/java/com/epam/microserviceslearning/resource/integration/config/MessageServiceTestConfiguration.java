package com.epam.microserviceslearning.resource.integration.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@EnableAutoConfiguration
@ImportAutoConfiguration(TestChannelBinderConfiguration.class)
@ComponentScan(basePackages = {
        "com.epam.microserviceslearning.resource.service.messaging",
        "com.epam.microserviceslearning.common.logging"
})
@Profile("test")
public class MessageServiceTestConfiguration {

}
