package com.epam.microserviceslearning.processor.component.steps;

import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.processor.component.client.RabbitClient;
import com.epam.microserviceslearning.processor.model.IdDto;
import com.epam.microserviceslearning.processor.model.SongMetadataDto;
import com.epam.microserviceslearning.processor.utils.WireMockUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.InputStream;
import java.time.Duration;
import java.util.concurrent.Callable;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class ProcessingFeatureSteps {
    @Autowired
    @Qualifier("resource-service")
    private WireMockServer resourceService;

    @Autowired
    @Qualifier("song-service")
    private WireMockServer songService;

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

        final IdDto idDto = new IdDto();
        idDto.setId(resourceId);

        final InputStream file = TestUtils.loadFileFromResources(filename);
        WireMockUtils.addGetStub(resourceService, "/resources/.+", file.readAllBytes());
        WireMockUtils.addPostStub(songService, "/songs", gson.toJson(metadataDto), gson.toJson(idDto));
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
                () -> resourceService.verify(getRequestedFor(urlEqualTo("/resources/" + id)))
        );

        await().atMost(Duration.ofSeconds(5))
                .until(conditionEvaluator);
    }

    @And("^metadata (\\d+), (.+), (.+), (.+), (.+), (.+) is sent to song-service$")
    public void verifySongServiceRequest(long resourceId, String name, String artist, String album, String length, int year) {
        final SongMetadataDto metadataDto = SongMetadataDto.builder()
                .resourceId(resourceId)
                .name(name)
                .artist(artist)
                .album(album)
                .length(length)
                .year(year)
                .build();
        final String json = gson.toJson(metadataDto);

        final Callable<Boolean> conditionEvaluator = getConditionEvaluator(
                () -> songService.verify(
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
