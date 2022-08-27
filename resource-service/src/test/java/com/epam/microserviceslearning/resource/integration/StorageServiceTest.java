package com.epam.microserviceslearning.resource.integration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.resource.client.StorageServiceClient;
import com.epam.microserviceslearning.resource.integration.config.StorageServiceTestConfiguration;
import com.epam.microserviceslearning.resource.persistence.storage.StorageService;
import com.epam.microserviceslearning.resource.service.model.StorageMetadataDto;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import static com.epam.microserviceslearning.common.storage.factory.StorageProvider.S3;
import static com.epam.microserviceslearning.common.storage.factory.StorageType.STAGING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StorageServiceTestConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "aws.s3.bucket.name=test"
})
class StorageServiceTest {
    private static final long STORAGE_ID = 1;
    private static final StorageType STORAGE_TYPE = STAGING;
    private static final String BUCKET_NAME = "test";
    private static final String OBJECT_KEY = UUID.randomUUID().toString();

    @Autowired
    private StorageService storageService;

    @Autowired
    private AmazonS3 s3;

    @MockBean
    private StorageServiceClient storageServiceClient;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        final File file = File.createTempFile("test", "");
        final InputStream inputStream = TestUtils.loadFileFromResources("test_data/valid.mp3");
        FileUtils.copyInputStreamToFile(inputStream, file);

        final PutObjectRequest putRequest = new PutObjectRequest(BUCKET_NAME, OBJECT_KEY, file);
        s3.createBucket(BUCKET_NAME);
        s3.putObject(putRequest);

        final StorageMetadataDto storageMetadataDto = new StorageMetadataDto();
        storageMetadataDto.setId(1);
        storageMetadataDto.setProvider(S3);
        storageMetadataDto.setDescriptor(BUCKET_NAME);

        when(storageServiceClient.getStorageByType(STORAGE_TYPE)).thenReturn(storageMetadataDto);
        when(storageServiceClient.getStorageById(STORAGE_ID)).thenReturn(storageMetadataDto);
    }

    @SneakyThrows
    @Test
    void shouldStoreBinary() {
        // given
        final InputStream dummyInputStream = TestUtils.loadFileFromResources("test_data/valid.mp3");
        final String filename = UUID.randomUUID().toString();

        // when
        storageService.store(dummyInputStream, filename, STAGING);

        // then
        final InputStream uploadedFile = s3.getObject(BUCKET_NAME, filename).getObjectContent();
        assertThat(uploadedFile).isNotEmpty();

    }

    @SneakyThrows
    @Test
    void shouldReadBinary() {
        // when
        final InputStream inputStream = storageService.read(STORAGE_ID, OBJECT_KEY);

        // then
        assertThat(inputStream).isNotEmpty();
    }

    @Test
    void shouldDeleteBinary() {
        // when
        storageService.delete(STORAGE_ID, OBJECT_KEY);

        // then
        final S3Object deletedFile = s3.getObject(BUCKET_NAME, OBJECT_KEY);
        assertThat(deletedFile).isNull();
    }
}
