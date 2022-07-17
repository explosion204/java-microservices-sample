package com.epam.microserviceslearning.e2e;

import com.epam.microserviceslearning.e2e.model.SongMetadata;
import com.epam.microserviceslearning.e2e.provider.ServiceProvider;
import com.epam.microserviceslearning.e2e.client.ResourceServiceClient;
import com.epam.microserviceslearning.e2e.client.SongServiceClient;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Slf4j
public class E2ETestSteps {
    private static final ServiceProvider serviceProvider = new ServiceProvider();
    private static ResourceServiceClient resourceServiceClient;
    private static SongServiceClient songServiceClient;

    @BeforeAll
    public static void beforeAll() {
        serviceProvider.launchEnvironment();
        resourceServiceClient = new ResourceServiceClient(serviceProvider.getResourceServiceUrl());
        songServiceClient = new SongServiceClient(serviceProvider.getSongServiceUrl());
    }

    @Given("services are up and running")
    public void checkServicesStatus() {
        final boolean resourceServiceAvailable = resourceServiceClient.ping();
        final boolean songServiceAvailable = songServiceClient.ping();
        assertThat(resourceServiceAvailable).isTrue();
        assertThat(songServiceAvailable).isTrue();
    }

    @SneakyThrows
    @When("I upload sample MP3 file to resource-service")
    public void uploadFile() {
        final byte[] data = getBytesData("test_data/sample.mp3");
        resourceServiceClient.upload(data);
    }

    @Then("the file is successfully loaded to storage")
    public void checkFileIsLoadedToStorage() {
        await().atMost(Duration.ofSeconds(5))
                .until(() -> resourceServiceClient.download(1).isPresent());

        final byte[] data = resourceServiceClient.download(1)
                .orElseThrow();

        assertThat(data).hasSizeGreaterThan(0);
    }

    @And("the song metadata is successfully parsed and stored in song-db")
    public void checkSongDbIsUpdated() {
        await().atMost(Duration.ofSeconds(5))
                .until(() -> metadataIsProcessed(1));

        final SongMetadata metadata = songServiceClient.getMetadata(1)
                .orElseThrow();

        assertThat(metadata).extracting(SongMetadata::getArtist)
                .isEqualTo("Jamie Christopherson");
    }

    @SneakyThrows
    private byte[] getBytesData(String filename) {
        return getClass().getClassLoader()
                .getResourceAsStream(filename)
                .readAllBytes();
    }

    private boolean metadataIsProcessed(long id) {
        return songServiceClient.getMetadata(id).isPresent();
    }
}
