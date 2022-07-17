package com.epam.microserviceslearning.resource.integration.mock;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AbstractAmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AmazonS3MockClient extends AbstractAmazonS3 {
    private final Map<String, Map<String, S3Object>> buckets = new HashMap<>();

    @Override
    public Bucket createBucket(String bucketName) {
        buckets.put(bucketName, new HashMap<>());
        return new Bucket();
    }

    @Override
    public boolean doesBucketExistV2(String bucketName) {
        return buckets.containsKey(bucketName);
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest putObjectRequest) {
        try {
            final String bucketName = putObjectRequest.getBucketName();
            final String key = putObjectRequest.getKey();
            final InputStream content = new FileInputStream(putObjectRequest.getFile());


            final Map<String, S3Object> bucket = buckets.get(bucketName);

            final S3Object object = new S3Object();
            object.setObjectContent(content);

            bucket.put(key, object);

            return new PutObjectResult();
        } catch (Exception e) {
            throw new AmazonServiceException(StringUtils.EMPTY, e);
        }
    }

    @Override
    public boolean doesObjectExist(String bucketName, String objectName) {
        final Map<String, S3Object> bucket = buckets.get(bucketName);
        return bucket != null && bucket.containsKey(objectName);
    }

    @Override
    public S3Object getObject(String bucketName, String key) {
        try {
            final Map<String, S3Object> bucket = buckets.get(bucketName);
            return bucket.get(key);
        } catch (Exception e) {
            throw new AmazonServiceException(StringUtils.EMPTY, e);
        }
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        try {
            final Map<String, S3Object> bucket = buckets.get(bucketName);
            bucket.remove(key);
        } catch (Exception e) {
            throw new AmazonServiceException(StringUtils.EMPTY, e);
        }
    }
}
