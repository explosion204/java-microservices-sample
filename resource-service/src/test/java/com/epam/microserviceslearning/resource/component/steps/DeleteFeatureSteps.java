package com.epam.microserviceslearning.resource.component.steps;

import com.epam.microserviceslearning.resource.component.client.ResourceServiceClient;
import com.epam.microserviceslearning.resource.component.client.model.BinaryObjectIdListDto;
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
    private ResourceServiceClient client;

    private ResponseEntity<BinaryObjectIdListDto> response;

    @When("^user tries to delete file with ids = (.+)$")
    public void deleteFiles(String idsString) {
        final List<Long> ids = parseIdList(idsString);

        response = client.delete(ids);
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
                .extracting(BinaryObjectIdListDto::getIds)
                .isEqualTo(parseIdList(idsString));
    }

    private List<Long> parseIdList(String idsString) {
        return Arrays.stream(idsString.split(","))
                .map(Long::parseLong)
                .toList();
    }
}
