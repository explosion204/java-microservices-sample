package com.epam.microserviceslearning.resource.component.steps;

import com.epam.microserviceslearning.resource.component.client.ResourceServiceClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class DownloadFeatureSteps {
    @Autowired
    private ResourceServiceClient client;

    private ResponseEntity<byte[]> response;

    @When("^user tries to download file with id = (\\d+)$")
    public void downloadFile(long id) {
        response = client.downloadFile(id);
    }

    @Then("^user receives status code (\\d+) for download request$")
    public void verifyStatusCode(int expectedStatusCode) {
        assertThat(response)
                .extracting(ResponseEntity::getStatusCodeValue)
                .isEqualTo(expectedStatusCode);
    }

    @And("^response has a Content-Type header = (.+)$")
    public void verifyContentType(String expectedContentType) {
        assertThat(response)
                .extracting(ResponseEntity::getHeaders)
                .extracting(HttpHeaders::getContentType)
                .extracting(MediaType::toString)
                .isEqualTo(expectedContentType);
    }
}
