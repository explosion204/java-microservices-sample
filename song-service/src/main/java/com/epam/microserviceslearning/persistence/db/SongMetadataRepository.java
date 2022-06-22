package com.epam.microserviceslearning.persistence.db;

import com.epam.microserviceslearning.domain.SongMetadata;

import java.util.Optional;

public interface SongMetadataRepository {
    Optional<SongMetadata> findById(long resourceId);
    SongMetadata save(SongMetadata songMetadata);
    void delete(SongMetadata songMetadata);
}
