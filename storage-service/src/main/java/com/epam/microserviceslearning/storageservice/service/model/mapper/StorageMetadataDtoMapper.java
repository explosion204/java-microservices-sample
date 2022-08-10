package com.epam.microserviceslearning.storageservice.service.model.mapper;

import com.epam.microserviceslearning.storageservice.domain.StorageMetadata;
import com.epam.microserviceslearning.storageservice.service.model.StorageMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StorageMetadataDtoMapper {
    @Mapping(ignore = true, target = "id")
    StorageMetadata toStorageMetadata(StorageMetadataDto storageMetadataDto);

    StorageMetadataDto toStorageMetadataDto(StorageMetadata storageMetadata);
    List<StorageMetadataDto> toStorageMetadataDtoList(List<StorageMetadata> storageMetadataList);
}
