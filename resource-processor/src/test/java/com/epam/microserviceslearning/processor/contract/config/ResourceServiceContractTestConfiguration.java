package com.epam.microserviceslearning.processor.contract.config;

import com.epam.microserviceslearning.processor.client.ResourceServiceClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@EnableAutoConfiguration
@EnableFeignClients(clients = { ResourceServiceClient.class })
@Profile("test")
public class ResourceServiceContractTestConfiguration {

}
