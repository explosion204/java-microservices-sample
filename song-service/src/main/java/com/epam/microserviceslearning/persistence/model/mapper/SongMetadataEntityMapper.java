package com.epam.microserviceslearning.persistence.model.mapper;

import com.epam.microserviceslearning.domain.SongMetadata;
import com.epam.microserviceslearning.persistence.model.SongMetadataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SongMetadataEntityMapper {
    SongMetadata toSongMetadata(SongMetadataEntity songMetadataEntity);
    SongMetadataEntity tooSongMetadataEntity(SongMetadata songMetadata);
}
