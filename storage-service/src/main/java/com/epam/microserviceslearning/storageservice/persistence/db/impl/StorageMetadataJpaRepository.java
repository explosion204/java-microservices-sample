package com.epam.microserviceslearning.storageservice.persistence.db.impl;

import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.storageservice.persistence.model.StorageMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageMetadataJpaRepository extends JpaRepository<StorageMetadataEntity, Long> {
    List<StorageMetadataEntity> findByType(StorageType type);
}
