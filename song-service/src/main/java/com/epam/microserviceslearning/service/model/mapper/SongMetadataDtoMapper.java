package com.epam.microserviceslearning.service.model.mapper;

import com.epam.microserviceslearning.domain.SongMetadata;
import com.epam.microserviceslearning.service.model.SongMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SongMetadataDtoMapper {
    SongMetadata toSongMetadata(SongMetadataDto songMetadataDto);
    SongMetadataDto toSongMetadataDto(SongMetadata songMetadata);
}
