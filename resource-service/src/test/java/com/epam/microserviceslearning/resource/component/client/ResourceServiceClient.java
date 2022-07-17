package com.epam.microserviceslearning.resource.component.client;

import com.epam.microserviceslearning.resource.component.client.model.BinaryObjectIdDto;
import com.epam.microserviceslearning.resource.component.client.model.BinaryObjectIdListDto;
import io.cucumber.spring.CucumberTestContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
@RequiredArgsConstructor
public class ResourceServiceClient {
    private static final String UPLOAD_URL_PATTERN = "http://localhost:%s/resources";
    private static final String DOWNLOAD_URL_PATTERN = "http://localhost:%s/resources/%s";
    private static final String DELETE_URL_PATTERN = "http://localhost:%s/resources?id=%s";

    private final TestRestTemplate restTemplate;
    private String uploadUrl;

    @LocalServerPort
    private int port;

    @PostConstruct
    void init() {
        uploadUrl = String.format(UPLOAD_URL_PATTERN, port);
    }

    @SneakyThrows
    public ResponseEntity<BinaryObjectIdDto> uploadFile(InputStream file) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "audio/mpeg");

        final HttpEntity<byte[]> entity = new HttpEntity<>(IOUtils.toByteArray(file), headers);

        return restTemplate.postForEntity(uploadUrl, entity, BinaryObjectIdDto.class);
    }

    public ResponseEntity<byte[]> downloadFile(long id) {
        final String downloadUrl = String.format(DOWNLOAD_URL_PATTERN, port, id);

        return restTemplate.getForEntity(downloadUrl, byte[].class);
    }

    public ResponseEntity<BinaryObjectIdListDto> delete(List<Long> ids) {
        final String idCsv = StringUtils.join(ids, ",");
        final String deleteUrl = String.format(DELETE_URL_PATTERN, port, idCsv);

        return restTemplate.exchange(deleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, BinaryObjectIdListDto.class);
    }
}
