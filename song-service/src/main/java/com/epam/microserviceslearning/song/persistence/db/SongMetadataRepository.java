package com.epam.microserviceslearning.song.persistence.db;

import com.epam.microserviceslearning.song.domain.SongMetadata;

import java.util.Optional;

public interface SongMetadataRepository {
    Optional<SongMetadata> findById(long resourceId);
    SongMetadata save(SongMetadata songMetadata);
    void delete(SongMetadata songMetadata);
}
