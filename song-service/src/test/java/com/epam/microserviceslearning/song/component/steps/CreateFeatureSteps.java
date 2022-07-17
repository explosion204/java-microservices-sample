package com.epam.microserviceslearning.song.component.steps;

import com.epam.microserviceslearning.song.component.client.SongServiceClient;
import com.epam.microserviceslearning.song.component.client.model.SongMetadataDto;
import com.epam.microserviceslearning.song.component.client.model.SongMetadataIdDto;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateFeatureSteps {
    private static final String NULL_VALUE = "null";

    @Autowired
    private SongServiceClient client;

    private ResponseEntity<SongMetadataIdDto> response;

    @When("^user sends request with body: (\\d+), (.+), (.+), (.+), (.+), (.+)$")
    public void createMetadata(long resourceId, String name, String artist, String album, String length, int year) {
        final SongMetadataDto metadataDto = new SongMetadataDto();
        metadataDto.setResourceId(resourceId);
        metadataDto.setName(name.equals(NULL_VALUE) ? null : name);
        metadataDto.setArtist(artist.equals(NULL_VALUE) ? null : artist);
        metadataDto.setAlbum(album.equals(NULL_VALUE) ? null : album);
        metadataDto.setLength(length.equals(NULL_VALUE) ? null : length);
        metadataDto.setYear(year);

        response = client.createMetadata(metadataDto);
    }

    @Then("^user receives status code (\\d+) for create request$")
    public void verifyStatusCode(int expectedStatusCode) {
        assertThat(response)
                .extracting(ResponseEntity::getStatusCodeValue)
                .isEqualTo(expectedStatusCode);
    }
}
