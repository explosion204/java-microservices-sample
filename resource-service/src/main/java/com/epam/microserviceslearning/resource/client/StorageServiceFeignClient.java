package com.epam.microserviceslearning.resource.client;

import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.common.storage.impl.LocalStorage;
import com.epam.microserviceslearning.resource.service.model.StorageMetadataDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.epam.microserviceslearning.common.storage.factory.StorageProvider.LOCAL;

@FeignClient(value = "storage-service-client", url = "${service-gateway.url}")
public interface StorageServiceFeignClient {
    @GetMapping(value = "/storages/random/{type}")
    @Retry(name = "rest-retry")
    @CircuitBreaker(name = "rest-circuit-breaker", fallbackMethod = "getStorageByTypeFallback")
    StorageMetadataDto getStorageByType(@PathVariable("type") StorageType type);

    @GetMapping(value = "/storages/{id}")
    @Retry(name = "rest-retry")
    @CircuitBreaker(name = "rest-circuit-breaker", fallbackMethod = "getStorageByIdFallback")
    StorageMetadataDto getStorageById(@PathVariable("id") long storageId);

    default StorageMetadataDto getStorageByTypeFallback(StorageType type, Throwable e) {
        return getFallbackStorage();
    }

    default StorageMetadataDto getStorageByIdFallback(long id, Throwable e) {
        return getFallbackStorage();
    }

    private StorageMetadataDto getFallbackStorage() {
        final StorageMetadataDto storageMetadata = new StorageMetadataDto();
        storageMetadata.setProvider(LOCAL);
        storageMetadata.setId(LocalStorage.LOCAL_STORAGE_ID);
        return storageMetadata;
    }
}
