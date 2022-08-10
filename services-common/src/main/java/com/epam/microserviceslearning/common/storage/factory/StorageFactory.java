package com.epam.microserviceslearning.common.storage.factory;

import com.amazonaws.services.s3.AmazonS3;
import com.epam.microserviceslearning.common.storage.impl.LocalStorage;
import com.epam.microserviceslearning.common.storage.impl.S3Storage;
import com.epam.microserviceslearning.common.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageFactory {
    private final AmazonS3 s3;

    @Value("${java.io.tmpdir}")
    private String tempDirectory;

    public Storage getStorage(StorageProvider provider, String descriptor) {
        return switch (provider) {
            case S3 -> new S3Storage(s3, descriptor);
            case LOCAL -> new LocalStorage(tempDirectory);
        };
    }
}
