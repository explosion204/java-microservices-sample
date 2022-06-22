package com.epam.microserviceslearning.resource.persistence.storage.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.microserviceslearning.resource.exception.BinaryUploadException;
import com.epam.microserviceslearning.resource.persistence.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService implements StorageService {
    private final AmazonS3 s3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @PostConstruct
    void init() {
        if (!s3.doesBucketExistV2(bucketName)) {
            s3.createBucket(bucketName);
        }
    }

    @SneakyThrows
    @Override
    public void store(InputStream file, String key) {
        final File tmpFile = File.createTempFile("upload_tmp", key);
        FileUtils.copyInputStreamToFile(file, tmpFile);

        try {
            final PutObjectRequest request = new PutObjectRequest(bucketName, key, tmpFile);
            s3.putObject(request);

        } catch (Exception e) {
            throw new BinaryUploadException(e);

        } finally {
            tmpFile.delete();
        }
    }

    @Override
    public InputStream read(String key) {
        if (!s3.doesObjectExist(bucketName, key)) {
            log.error("Unable to find object with key '{}'", key);
            return InputStream.nullInputStream();
        }

        final S3Object object = s3.getObject(bucketName, key);
        return object.getObjectContent();
    }

    @Override
    public void delete(String key) {
        if (!s3.doesObjectExist(bucketName, key)) {
            log.warn("Unable to find object with key '{}'", key);
        }

        s3.deleteObject(bucketName, key);
    }
}
