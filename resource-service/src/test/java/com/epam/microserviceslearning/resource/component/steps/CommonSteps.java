package com.epam.microserviceslearning.resource.component.steps;

import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.resource.component.client.ResourceServiceClient;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {
    @Autowired
    private ResourceServiceClient client;

    @Given("^file (.+) is successfully uploaded$")
    public void uploadFile(String filename) {
        final InputStream file = TestUtils.loadFileFromResources(filename);
        final int statusCode = client.uploadFile(file)
                .getStatusCode()
                .value();

        assertThat(statusCode).isEqualTo(200);
    }
}
