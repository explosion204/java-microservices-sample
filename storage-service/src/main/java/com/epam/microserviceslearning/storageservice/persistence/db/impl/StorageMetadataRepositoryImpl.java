package com.epam.microserviceslearning.storageservice.persistence.db.impl;

import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.storageservice.domain.StorageMetadata;
import com.epam.microserviceslearning.storageservice.persistence.db.StorageMetadataRepository;
import com.epam.microserviceslearning.storageservice.persistence.model.StorageMetadataEntity;
import com.epam.microserviceslearning.storageservice.persistence.model.mapper.StorageMetadataEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StorageMetadataRepositoryImpl implements StorageMetadataRepository {
    private final StorageMetadataJpaRepository jpaRepository;
    private final StorageMetadataEntityMapper mapper;

    @Override
    public List<StorageMetadata> findAll() {
        final List<StorageMetadataEntity> entities = jpaRepository.findAll();
        return mapper.toStorageMetadataList(entities);
    }

    @Override
    public Optional<StorageMetadata> findById(long id) {
        final Optional<StorageMetadataEntity> entity = jpaRepository.findById(id);

        if (entity.isPresent()) {
            final StorageMetadata storageMetadata = mapper.toStorageMetadata(entity.get());
            return Optional.of(storageMetadata);
        }

        return Optional.empty();
    }

    @Override
    public List<StorageMetadata> findByType(StorageType type) {
        final List<StorageMetadataEntity> entities = jpaRepository.findByType(type);
        return mapper.toStorageMetadataList(entities);
    }

    @Override
    public StorageMetadata save(StorageMetadata storageMetadata) {
        final StorageMetadataEntity entity = mapper.toStorageMetadataEntity(storageMetadata);
        final StorageMetadataEntity updatedEntity = jpaRepository.save(entity);
        return mapper.toStorageMetadata(updatedEntity);
    }

    @Override
    public void delete(StorageMetadata storageMetadata) {
        final StorageMetadataEntity entity = mapper.toStorageMetadataEntity(storageMetadata);
        jpaRepository.delete(entity);
    }
}
