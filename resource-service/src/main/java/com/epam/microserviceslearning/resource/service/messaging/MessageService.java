package com.epam.microserviceslearning.resource.service.messaging;

import com.epam.microserviceslearning.common.logging.LoggingService;
import com.epam.microserviceslearning.common.logging.trace.AmqpTraceUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final LoggingService logger;
    private final StreamBridge streamBridge;
    private final Gson gson;
    private final AmqpTraceUtils amqpTraceUtils;

    @Value("${service.messaging.binary-processing-binding}")
    private String binaryProcessingBinding;

    public <T> void sendMessage(T body) {
        final String payload = gson.toJson(body);
        final Message<String> message = MessageBuilder
                .withPayload(payload)
                .build();

        final Message<String> postProcessedMessage = amqpTraceUtils.postProcessMessage(message);
        streamBridge.send(binaryProcessingBinding, postProcessedMessage);
        logger.info("Sent a message %s", message);
    }
}
