package com.epam.microserviceslearning.storageservice.service;

import com.epam.microserviceslearning.common.logging.LoggingService;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.storageservice.exception.NoStoragesException;
import com.epam.microserviceslearning.storageservice.domain.StorageMetadata;
import com.epam.microserviceslearning.storageservice.exception.StorageNotFoundException;
import com.epam.microserviceslearning.storageservice.persistence.db.StorageMetadataRepository;
import com.epam.microserviceslearning.storageservice.service.model.StorageMetadataDto;
import com.epam.microserviceslearning.storageservice.service.model.StorageMetadataIdDto;
import com.epam.microserviceslearning.storageservice.service.model.StorageMetadataIdListDto;
import com.epam.microserviceslearning.storageservice.service.model.mapper.StorageMetadataDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final LoggingService logger;
    private final StorageMetadataRepository repository;
    private final StorageMetadataDtoMapper mapper;

    private final Random random = new Random();

    public List<StorageMetadataDto> findAll() {
        final List<StorageMetadata> storages = repository.findAll();
        return mapper.toStorageMetadataDtoList(storages);
    }

    public StorageMetadataDto findById(long id) {
        return repository.findById(id)
                .map(mapper::toStorageMetadataDto)
                .orElseThrow(() -> new StorageNotFoundException(id));
    }

    public StorageMetadataDto findRandomByType(StorageType type) {
        final List<StorageMetadata> storages = repository.findByType(type);

        if (storages.isEmpty()) {
            throw new NoStoragesException();
        }

        final int index = random.nextInt(storages.size());
        final StorageMetadata storageMetadata = storages.get(index);
        return mapper.toStorageMetadataDto(storageMetadata);
    }

    public StorageMetadataIdDto create(StorageMetadataDto storageMetadataDto) {
        storageMetadataDto.setId(0); // always create

        final StorageMetadata storageMetadata = mapper.toStorageMetadata(storageMetadataDto);
        final StorageMetadata savedStorageMetadata = repository.save(storageMetadata);

        return StorageMetadataIdDto.builder()
                .id(savedStorageMetadata.getId())
                .build();
    }

    public StorageMetadataIdListDto delete(List<Long> ids) {
        final Map<Long, Boolean> deletionResult = new HashMap<>();
        ids.forEach(id -> deletionResult.put(id, delete(id)));

        final List<Long> deletedIds = deletionResult.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();

        return StorageMetadataIdListDto.builder()
                .ids(deletedIds)
                .build();
    }

    private boolean delete(long id) {
        final Optional<StorageMetadata> storageMetadataOpt = repository.findById(id);

        if (storageMetadataOpt.isEmpty()) {
            logger.warn("Unable to find storage with id = %s", id);
            return false;
        }

        repository.delete(storageMetadataOpt.get());
        return true;
    }
}
