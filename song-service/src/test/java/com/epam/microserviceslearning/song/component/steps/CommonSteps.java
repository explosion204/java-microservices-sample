package com.epam.microserviceslearning.song.component.steps;

import com.epam.microserviceslearning.song.component.client.SongServiceClient;
import com.epam.microserviceslearning.song.component.client.model.SongMetadataDto;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {
    private static final String NULL_VALUE = "null";

    @Autowired
    private SongServiceClient client;

    @Given("^metadata exists: (\\d+), (.+), (.+), (.+), (.+), (.+)$")
    public void createMetadata(long resourceId, String name, String artist, String album, String length, int year) {
        final SongMetadataDto metadataDto = new SongMetadataDto();
        metadataDto.setResourceId(resourceId);
        metadataDto.setName(name.equals(NULL_VALUE) ? null : name);
        metadataDto.setArtist(artist.equals(NULL_VALUE) ? null : artist);
        metadataDto.setAlbum(album.equals(NULL_VALUE) ? null : album);
        metadataDto.setLength(length.equals(NULL_VALUE) ? null : length);
        metadataDto.setYear(year);

        final int statusCode = client.createMetadata(metadataDto).getStatusCodeValue();
        assertThat(statusCode).isEqualTo(200);
    }
}
