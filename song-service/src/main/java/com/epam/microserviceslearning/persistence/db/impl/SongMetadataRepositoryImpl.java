package com.epam.microserviceslearning.persistence.db.impl;

import com.epam.microserviceslearning.domain.SongMetadata;
import com.epam.microserviceslearning.persistence.db.SongMetadataRepository;
import com.epam.microserviceslearning.persistence.model.SongMetadataEntity;
import com.epam.microserviceslearning.persistence.model.mapper.SongMetadataEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SongMetadataRepositoryImpl implements SongMetadataRepository {
    private final SongMetadataEntityJpaRepository jpaRepository;
    private final SongMetadataEntityMapper mapper;

    @Override
    public Optional<SongMetadata> findById(long id) {
        final Optional<SongMetadataEntity> entity = jpaRepository.findById(id);

        if (entity.isPresent()) {
            final SongMetadata songMetadata = mapper.toSongMetadata(entity.get());
            return Optional.of(songMetadata);
        }

        return Optional.empty();
    }

    @Override
    public SongMetadata save(SongMetadata songMetadata) {
        final SongMetadataEntity entity = mapper.tooSongMetadataEntity(songMetadata);
        final SongMetadataEntity updatedEntity = jpaRepository.save(entity);
        return mapper.toSongMetadata(updatedEntity);
    }

    @Override
    public void delete(SongMetadata songMetadata) {
        final SongMetadataEntity entity = mapper.tooSongMetadataEntity(songMetadata);
        jpaRepository.delete(entity);
    }
}
