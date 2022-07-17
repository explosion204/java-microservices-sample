package com.epam.microserviceslearning.resource.integration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.resource.integration.config.StorageServiceTestConfiguration;
import com.epam.microserviceslearning.resource.persistence.storage.StorageService;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StorageServiceTestConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "aws.s3.bucket.name=test"
})
class StorageServiceTest {
    private static final String BUCKET_NAME = "test";
    private static final String OBJECT_KEY = UUID.randomUUID().toString();

    @Autowired
    private StorageService storageService;

    @Autowired
    private AmazonS3 s3;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        final File file = File.createTempFile("test", "");
        final InputStream inputStream = TestUtils.loadFileFromResources("test_data/valid.mp3");
        FileUtils.copyInputStreamToFile(inputStream, file);

        final PutObjectRequest putRequest = new PutObjectRequest(BUCKET_NAME, OBJECT_KEY, file);
        s3.putObject(putRequest);
    }

    @SneakyThrows
    @Test
    void shouldStoreBinary() {
        // given
        final InputStream dummyInputStream = TestUtils.loadFileFromResources("test_data/valid.mp3");
        final String filename = UUID.randomUUID().toString();

        // when
        storageService.store(dummyInputStream, filename);

        // then
        final InputStream storedInputStream = storageService.read(filename);
        assertThat(storedInputStream).isNotEmpty();
    }

    @SneakyThrows
    @Test
    void shouldReadBinary() {
        // when
        InputStream inputStream = storageService.read(OBJECT_KEY);

        // then
        assertThat(inputStream).isNotEmpty();
    }

    @Test
    void shouldDeleteBinary() {
        // when
        storageService.delete(OBJECT_KEY);

        // then
        final InputStream inputStream = storageService.read(OBJECT_KEY);
        assertThat(inputStream).isEmpty();
    }
}
