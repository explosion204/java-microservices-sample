package com.epam.microserviceslearning.song.component.client;


import com.epam.microserviceslearning.song.component.client.model.SongMetadataDto;
import com.epam.microserviceslearning.song.component.client.model.SongMetadataIdDto;
import com.epam.microserviceslearning.song.component.client.model.SongMetadataIdListDto;
import io.cucumber.spring.CucumberTestContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
@RequiredArgsConstructor
public class SongServiceClient {
    private static final String CREATE_URL_PATTERN = "http://localhost:%s/songs";
    private static final String FIND_URL_PATTERN = "http://localhost:%s/songs/%s";
    private static final String DELETE_URL_PATTERN = "http://localhost:%s/songs?id=%s";

    private final TestRestTemplate restTemplate;
    private String uploadUrl;

    @LocalServerPort
    private int port;

    @PostConstruct
    void init() {
        uploadUrl = String.format(CREATE_URL_PATTERN, port);
    }

    public ResponseEntity<SongMetadataIdDto> createMetadata(SongMetadataDto metadataDto) {
        final HttpEntity<SongMetadataDto> entity = new HttpEntity<>(metadataDto);

        return restTemplate.postForEntity(uploadUrl, entity, SongMetadataIdDto.class);
    }

    public ResponseEntity<SongMetadataDto> findMetadata(long id) {
        final String findUrl = String.format(FIND_URL_PATTERN, port, id);

        return restTemplate.getForEntity(findUrl, SongMetadataDto.class);
    }

    public ResponseEntity<SongMetadataIdListDto> deleteMetadata(List<Long> ids) {
        final String idCsv = StringUtils.join(ids, ",");
        final String deleteUrl = String.format(DELETE_URL_PATTERN, port, idCsv);

        return restTemplate.exchange(deleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, SongMetadataIdListDto.class);
    }
}
