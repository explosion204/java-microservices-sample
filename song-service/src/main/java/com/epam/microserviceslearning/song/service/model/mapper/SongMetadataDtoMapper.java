package com.epam.microserviceslearning.song.service.model.mapper;

import com.epam.microserviceslearning.song.domain.SongMetadata;
import com.epam.microserviceslearning.song.service.model.SongMetadataDto;
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
