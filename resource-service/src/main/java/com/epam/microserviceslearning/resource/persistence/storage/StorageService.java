package com.epam.microserviceslearning.resource.persistence.storage;

import com.epam.microserviceslearning.common.storage.Storage;
import com.epam.microserviceslearning.common.storage.factory.StorageFactory;
import com.epam.microserviceslearning.common.storage.factory.StorageProvider;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.resource.client.StorageServiceClient;
import com.epam.microserviceslearning.resource.exception.BinaryUploadException;
import com.epam.microserviceslearning.resource.service.model.StorageMetadataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageServiceClient storageServiceClient;
    private final StorageFactory storageFactory;

    public long store(InputStream file, String key, StorageType storageType) {
        final StorageMetadataDto storageMetadata = storageServiceClient.getStorageByType(storageType);
        final Storage storage = getStorage(storageMetadata);

        try {
            storage.store(file, key);
        } catch (Exception e) {
            throw new BinaryUploadException(e);
        }

        return storageMetadata.getId();
    }

    public InputStream read(long storageId, String key) {
        final StorageMetadataDto storageMetadata = storageServiceClient.getStorageById(storageId);
        final Storage storage = getStorage(storageMetadata);
        return storage.read(key);
    }

    public void delete(long storageId, String key) {
        final StorageMetadataDto storageMetadata = storageServiceClient.getStorageById(storageId);
        final Storage storage = getStorage(storageMetadata);
        storage.delete(key);
    }

    private Storage getStorage(StorageMetadataDto storageMetadata) {
        final StorageProvider provider = storageMetadata.getProvider();
        final String descriptor = storageMetadata.getDescriptor();

        return storageFactory.getStorage(provider, descriptor);
    }
}
