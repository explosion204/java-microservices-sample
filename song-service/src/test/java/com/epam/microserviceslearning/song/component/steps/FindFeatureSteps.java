package com.epam.microserviceslearning.song.component.steps;

import com.epam.microserviceslearning.song.component.client.SongServiceClient;
import com.epam.microserviceslearning.song.component.client.model.SongMetadataDto;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class FindFeatureSteps {
    @Autowired
    private SongServiceClient client;

    private ResponseEntity<SongMetadataDto> response;

    @When("^user tries to find metadata with id = (\\d+)$")
    public void findMetadata(long id) {
        response = client.findMetadata(id);
    }

    @Then("^user receives status code (\\d+) for find request$")
    public void verifyStatusCode(int expectedStatusCode) {
        assertThat(response)
                .extracting(ResponseEntity::getStatusCodeValue)
                .isEqualTo(expectedStatusCode);
    }
}
