package com.epam.microserviceslearning.resource.component.steps;

import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.resource.component.client.RabbitClient;
import com.epam.microserviceslearning.resource.component.client.ResourceServiceClient;

import com.epam.microserviceslearning.resource.component.client.model.BinaryObjectIdDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class UploadFeatureSteps {
    @Autowired
    private ResourceServiceClient client;

    @Autowired
    private RabbitClient rabbitClient;

    private ResponseEntity<BinaryObjectIdDto> response;

    @SneakyThrows
    @When("^user uploads file (.+)$")
    public void uploadFile(String filename) {
        final InputStream file = TestUtils.loadFileFromResources(filename);

        response = client.uploadFile(file);
    }

    @Then("^user receives status code (\\d+) for upload request")
    public void checkStatusCode(int expectedStatusCode) {
        assertThat(response)
                .extracting(ResponseEntity::getStatusCodeValue)
                .isEqualTo(expectedStatusCode);
    }

    @And("^resource id = (\\d+) was sent to queue$")
    public void verifyResourceIdInQueue(long expectedId) {
        long actualId = rabbitClient.getId();
        assertThat(actualId).isEqualTo(expectedId);
    }
}
