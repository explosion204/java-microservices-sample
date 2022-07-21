package com.epam.microserviceslearning.song.persistence.db.impl;

import com.epam.microserviceslearning.song.persistence.model.SongMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongMetadataEntityJpaRepository extends JpaRepository<SongMetadataEntity, Long> {

}
