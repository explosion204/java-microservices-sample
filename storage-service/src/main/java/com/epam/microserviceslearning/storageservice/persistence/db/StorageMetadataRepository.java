package com.epam.microserviceslearning.storageservice.persistence.db;

import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.storageservice.domain.StorageMetadata;

import java.util.List;
import java.util.Optional;

public interface StorageMetadataRepository {
    List<StorageMetadata> findAll();
    Optional<StorageMetadata> findById(long id);
    List<StorageMetadata> findByType(StorageType type);
    StorageMetadata save(StorageMetadata storageMetadata);
    void delete(StorageMetadata storageMetadata);
}
