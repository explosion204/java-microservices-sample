package com.epam.microserviceslearning.song.component.steps;

import com.epam.microserviceslearning.song.component.client.SongServiceClient;
import com.epam.microserviceslearning.song.component.client.model.SongMetadataIdListDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteFeatureSteps {
    @Autowired
    private SongServiceClient client;

    private ResponseEntity<SongMetadataIdListDto> response;

    @When("^user tries to delete metadata with ids = (.+)$")
    public void deleteMetadata(String idsString) {
        final List<Long> ids = parseIdList(idsString);

        response = client.deleteMetadata(ids);
    }

    @Then("^user receives status code (\\d+) for delete request$")
    public void verifyStatusCode(int expectedStatusCode) {
        assertThat(response)
                .extracting(ResponseEntity::getStatusCodeValue)
                .isEqualTo(expectedStatusCode);
    }

    @And("^response contains the ids of deleted items: (.+)$")
    public void verifyResponseBody(String idsString) {
        assertThat(response)
                .extracting(ResponseEntity::getBody)
                .extracting(SongMetadataIdListDto::getIds)
                .isEqualTo(parseIdList(idsString));
    }

    private List<Long> parseIdList(String idsString) {
        return Arrays.stream(idsString.split(","))
                .map(Long::parseLong)
                .toList();
    }
}
