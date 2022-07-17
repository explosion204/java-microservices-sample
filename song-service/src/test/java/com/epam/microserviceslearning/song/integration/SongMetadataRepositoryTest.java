package com.epam.microserviceslearning.song.integration;

import com.epam.microserviceslearning.song.domain.SongMetadata;
import com.epam.microserviceslearning.song.integration.config.SongMetadataRepositoryTestConfiguration;
import com.epam.microserviceslearning.song.persistence.db.SongMetadataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = SongMetadataRepositoryTestConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:init.sql",
        "spring.sql.init.mode=always",
        "spring.jpa.defer-datasource-initialization=true"
})
class SongMetadataRepositoryTest {
    @Autowired
    private SongMetadataRepository repository;

    @Test
    void shouldFindMetadataById() {
        // when
        final Optional<SongMetadata> binaryObject = repository.findById(1);

        // then
        assertThat(binaryObject).isPresent();
    }

    @Test
    void shouldSaveMetadata() {
        // given
        final SongMetadata newMetadata = new SongMetadata();

        // when
        final SongMetadata savedMetadata = repository.save(newMetadata);

        // then
        assertThat(savedMetadata)
                .extracting(SongMetadata::getId)
                .isEqualTo(2L);
    }

    @Test
    void shouldDeleteMetadata() {
        // given
        final SongMetadata metadataToDelete = new SongMetadata();
        metadataToDelete.setId(1);

        // when
        repository.delete(metadataToDelete);

        // then
        assertThat(repository.findById(1)).isEmpty();
    }
}
