package com.epam.microserviceslearning.storageservice.persistence.model.mapper;

import com.epam.microserviceslearning.storageservice.domain.StorageMetadata;
import com.epam.microserviceslearning.storageservice.persistence.model.StorageMetadataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StorageMetadataEntityMapper {
    StorageMetadata toStorageMetadata(StorageMetadataEntity storageMetadataEntity);
    StorageMetadataEntity toStorageMetadataEntity(StorageMetadata storageMetadata);
    List<StorageMetadata> toStorageMetadataList(List<StorageMetadataEntity> storageMetadataEntityList);
}
