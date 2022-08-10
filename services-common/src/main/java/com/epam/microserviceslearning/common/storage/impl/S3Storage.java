package com.epam.microserviceslearning.common.storage.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.microserviceslearning.common.storage.Storage;
import com.epam.microserviceslearning.common.storage.exception.StorageException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;

@Slf4j
public class S3Storage implements Storage {
    private final AmazonS3 s3;
    private final String bucketName;

    public S3Storage(AmazonS3 s3, String bucketName) {
        this.s3 = s3;
        this.bucketName = bucketName;

        initBucket();
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
            throw new StorageException();

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

    private void initBucket() {
        if (!s3.doesBucketExistV2(bucketName)) {
            s3.createBucket(bucketName);
        }
    }

}
