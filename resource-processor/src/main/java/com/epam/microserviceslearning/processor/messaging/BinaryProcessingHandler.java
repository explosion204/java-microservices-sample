package com.epam.microserviceslearning.processor.messaging;

import com.epam.microserviceslearning.processor.client.ResourceServiceClient;
import com.epam.microserviceslearning.processor.client.SongServiceClient;
import com.epam.microserviceslearning.processor.model.SongMetadataDto;
import com.epam.microserviceslearning.processor.model.SongMetadataIdDto;
import com.epam.microserviceslearning.processor.service.SongMetadataExtractor;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class BinaryProcessingHandler {
    private final ResourceServiceClient resourceServiceClient;
    private final SongMetadataExtractor metadataExtractor;
    private final SongServiceClient songServiceClient;

    @Bean
    public Consumer<Message<String>> input() {
        return this::handle;
    }

    @SneakyThrows
    private void handle(Message<String> message) {
        long resourceId = Long.parseLong(message.getPayload());
        log.info("Received a message with resourceId = {}", resourceId);

        @Cleanup InputStream file = downloadFile(resourceId);
        final SongMetadataDto metadata = metadataExtractor.extract(file, resourceId);
        final long savedMetadataId = songServiceClient.saveMetadata(metadata).getId();
        log.info("Saved metadata with id = {}", savedMetadataId);
    }

    private InputStream downloadFile(long id) {
        final byte[] binaryData = resourceServiceClient.downloadFile(id);
        return new ByteArrayInputStream(binaryData);
    }
}
