package com.epam.microserviceslearning.processor.unit;

import com.epam.microserviceslearning.processor.ResourceProcessorConfiguration;
import com.epam.microserviceslearning.processor.model.SongMetadataDto;
import com.epam.microserviceslearning.processor.service.SongMetadataExtractor;
import io.github.resilience4j.retry.internal.InMemoryRetryRegistry;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ResourceProcessorConfiguration.class,
        SongMetadataExtractor.class,
        InMemoryRetryRegistry.class
})
class SongMetadataExtractorTest {
    @Autowired
    private SongMetadataExtractor uut;

    @Test
    @SneakyThrows
    void shouldExtractMetadata() {
        // given
        @Cleanup final InputStream file = loadFile("test_data/sample.mp3");
        final long resourceId = 1;

        final SongMetadataDto expectedDto = SongMetadataDto.builder()
                .resourceId(resourceId)
                .name("A Stranger I Remain (Maniac Agenda Mix)")
                .artist("Jamie Christopherson")
                .album("Metal Gear Rising: Revengeance (Vocal Tracks)")
                .length("2:25")
                .year(2013)
                .build();

        // when
        final SongMetadataDto actualDto = uut.extract(file, resourceId);

        // then
        assertEquals(expectedDto, actualDto);
    }

    @SneakyThrows
    private InputStream loadFile(String name) {
        final Resource resource = new ClassPathResource(name);
        return resource.getInputStream();
    }
}