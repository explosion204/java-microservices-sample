package com.epam.microserviceslearning.resource.client;

import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.resource.service.model.StorageMetadataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * This class proxies feign client to support caching.
 * */
@Service
@RequiredArgsConstructor
public class StorageServiceClient {
    private final StorageServiceFeignClient client;

    public StorageMetadataDto getStorageByType(StorageType type) {
        return client.getStorageByType(type);
    }

    @Cacheable(cacheNames = "storages", key = "#storageId")
    public StorageMetadataDto getStorageById(long storageId) {
        return client.getStorageById(storageId);
    }
}
