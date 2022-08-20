package com.epam.microserviceslearning.processor.component.steps;

import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.common.testutils.WireMockUtils;
import com.epam.microserviceslearning.processor.component.client.RabbitClient;
import com.epam.microserviceslearning.processor.model.IdDto;
import com.epam.microserviceslearning.processor.model.SongMetadataDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.time.Duration;
import java.util.concurrent.Callable;

import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class ProcessingFeatureSteps {
    @Autowired
    private WireMockServer serviceGateway;

    @Autowired
    private Gson gson;

    @Autowired
    private RabbitClient rabbitClient;

    @SneakyThrows
    @Given("^file (.+) is loaded with expected metadata: (\\d+), (.+), (.+), (.+), (.+), (.+)$")
    public void setupMocks(String filename, long resourceId, String name, String artist, String album, String length, int year) {
        final SongMetadataDto metadataDto = SongMetadataDto.builder()
                .resourceId(resourceId)
                .name(name)
                .artist(artist)
                .album(album)
                .length(length)
                .year(year)
                .build();

        final IdDto reloadedIdDto = new IdDto();
        reloadedIdDto.setId(resourceId + 1);

        final InputStream file = TestUtils.loadFileFromResources(filename);
        serviceGateway.resetAll();
        WireMockUtils.addGetStub(serviceGateway, "/resources/.+", file.readAllBytes());
        WireMockUtils.addDeleteStub(serviceGateway, "/resources\\?id=.+", null);
        WireMockUtils.addPostStubForAnyRequestBody(serviceGateway, "/resources\\?storageType=PERMANENT", gson.toJson(reloadedIdDto));
        WireMockUtils.addPostStub(serviceGateway, "/songs", gson.toJson(metadataDto), gson.toJson(reloadedIdDto));
    }

    @When("^file id = (\\d+) is sent to input queue$")
    public void sendIdToQueue(long id) {
        final IdDto idDto = new IdDto();
        idDto.setId(id);

        rabbitClient.send(idDto);
    }

    @Then("^file id = (\\d+) is requested from resource-service$")
    public void verifyResourceServiceRequest(long id) {
        final Callable<Boolean> conditionEvaluator = getConditionEvaluator(
                () -> serviceGateway.verify(getRequestedFor(urlEqualTo("/resources/" + id)))
        );

        await().atMost(Duration.ofSeconds(5))
                .until(conditionEvaluator);
    }

    @And("^file id = (\\d+) is deleted from STAGING storage and uploaded to a PERMANENT one with new id$")
    public void verifyFileReload(long resourceId) {
        final Callable<Boolean> conditionEvaluator = getConditionEvaluator(
                () -> {
                    serviceGateway.verify(deleteRequestedFor(urlEqualTo("/resources?id=" + resourceId)));
                    serviceGateway.verify(postRequestedFor(urlEqualTo("/resources?storageType=PERMANENT")));
                }
        );

        await().atMost(Duration.ofSeconds(5))
                .until(conditionEvaluator);
    }

    @And("^metadata with new \\(incremented\\) (\\d+), (.+), (.+), (.+), (.+), (.+) is sent to song-service$")
    public void verifySongServiceRequest(long resourceId, String name, String artist, String album, String length, int year) {
        final SongMetadataDto metadataDto = SongMetadataDto.builder()
                .resourceId(resourceId + 1)
                .name(name)
                .artist(artist)
                .album(album)
                .length(length)
                .year(year)
                .build();
        final String json = gson.toJson(metadataDto);

        final Callable<Boolean> conditionEvaluator = getConditionEvaluator(
                () -> serviceGateway.verify(
                        postRequestedFor(urlEqualTo("/songs"))
                                .withRequestBody(equalTo(json))
                )
        );

        await().atMost(Duration.ofSeconds(5))
                .until(conditionEvaluator);
    }

    private Callable<Boolean> getConditionEvaluator(Runnable action) {
        return () -> {
            try {
                action.run();
                return true;
            } catch (Throwable e) {
                return false;
            }
        };
    }
}
