package com.epam.microserviceslearning.persistence.db.impl;

import com.epam.microserviceslearning.persistence.model.SongMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongMetadataEntityJpaRepository extends JpaRepository<SongMetadataEntity, Long> {

}
