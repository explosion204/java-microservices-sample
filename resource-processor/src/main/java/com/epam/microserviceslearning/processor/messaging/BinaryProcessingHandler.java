package com.epam.microserviceslearning.processor.messaging;

import com.epam.microserviceslearning.common.logging.LoggingService;
import com.epam.microserviceslearning.common.logging.trace.AmqpTraceUtils;
import com.epam.microserviceslearning.processor.client.ResourceServiceClient;
import com.epam.microserviceslearning.processor.client.SongServiceClient;
import com.epam.microserviceslearning.processor.model.IdDto;
import com.epam.microserviceslearning.processor.model.SongMetadataDto;
import com.epam.microserviceslearning.processor.service.SongMetadataExtractor;
import com.google.gson.Gson;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Consumer;

import static com.epam.microserviceslearning.common.storage.factory.StorageType.PERMANENT;

@Component
@RequiredArgsConstructor
public class BinaryProcessingHandler {
    private final LoggingService logger;
    private final ResourceServiceClient resourceServiceClient;
    private final SongMetadataExtractor metadataExtractor;
    private final SongServiceClient songServiceClient;
    private final AmqpTraceUtils amqpTraceUtils;
    private final Gson gson;

    @Bean
    public Consumer<Message<String>> input() {
        return amqpTraceUtils.buildProxy(this::handle);
    }

    @SneakyThrows
    private void handle(Message<String> message) {
        long resourceId = gson.fromJson(message.getPayload(), IdDto.class).getId();

        logger.info("Received a message with resourceId = %s", resourceId);

        @Cleanup InputStream file = downloadFile(resourceId);
        final SongMetadataDto metadata = metadataExtractor.extract(file);

        long processedResourceId = reuplaodFile(resourceId, file);
        metadata.setResourceId(processedResourceId);

        logger.info("Processed resource id = %s", processedResourceId);

        final long savedMetadataId = songServiceClient.saveMetadata(metadata).getId();
        logger.info("Saved metadata with id = %s", savedMetadataId);

    }

    private InputStream downloadFile(long id) {
        final byte[] binaryData = resourceServiceClient.downloadFile(id);
        return new ByteArrayInputStream(binaryData);
    }

    @SneakyThrows
    private long reuplaodFile(long id, InputStream file) {
        resourceServiceClient.deleteFile(id);
        return resourceServiceClient.uploadFile(IOUtils.toByteArray(file), PERMANENT)
                .getId();
    }
}
