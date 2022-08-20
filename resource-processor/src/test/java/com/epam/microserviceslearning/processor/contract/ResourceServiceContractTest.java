package com.epam.microserviceslearning.processor.contract;

import com.epam.microserviceslearning.processor.client.ResourceServiceClient;
import com.epam.microserviceslearning.processor.contract.config.ResourceServiceContractTestConfiguration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = ResourceServiceContractTestConfiguration.class
)
@ExtendWith(SpringExtension.class)
@AutoConfigureStubRunner(
        ids = "com.epam.microserviceslearning:resource-service:+:stubs:8091",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@TestPropertySource(properties = {
        "service-gateway.url=http://localhost:8091"
})
class ResourceServiceContractTest {

    @Autowired
    private ResourceServiceClient client;

    @SneakyThrows
    @Test
    void shouldFulfillUploadContract() {
        byte[] output = client.downloadFile(1);
        assertThat(output).isNotEmpty();
    }
}
