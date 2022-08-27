package com.epam.microserviceslearning.resource.integration;

import com.epam.microserviceslearning.resource.domain.BinaryObject;
import com.epam.microserviceslearning.resource.integration.config.BinaryObjectRepositoryTestConfiguration;
import com.epam.microserviceslearning.resource.persistence.db.BinaryObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BinaryObjectRepositoryTestConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:init.sql",
        "spring.sql.init.mode=always",
        "spring.jpa.defer-datasource-initialization=true"
})
class BinaryObjectRepositoryTest {
    @Autowired
    private BinaryObjectRepository repository;

    @Test
    void shouldFindBinaryObjectById() {
        // when
        final Optional<BinaryObject> binaryObject = repository.findById(1);

        // then
        assertThat(binaryObject).isPresent();
    }

    @Test
    void shouldSaveBinaryObject() {
        // given
        final BinaryObject newBinaryObject = BinaryObject.builder()
                .filename("test_filename")
                .status(BinaryObject.Status.SUCCESS)
                .build();

        // when
        final BinaryObject savedBinaryObject = repository.save(newBinaryObject);

        // then
        assertThat(savedBinaryObject)
                .extracting(BinaryObject::getId)
                .isEqualTo(2L);
    }
}
