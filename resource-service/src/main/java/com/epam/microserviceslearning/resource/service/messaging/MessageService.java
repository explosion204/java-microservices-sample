package com.epam.microserviceslearning.resource.service.messaging;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final StreamBridge streamBridge;
    private final Gson gson;

    @Value("${service.messaging.binary-processing-binding}")
    private String binaryProcessingBinding;

    public <T> void sendMessage(T body) {
        final String payload = gson.toJson(body);
        final Message<String> message = MessageBuilder
                .withPayload(payload)
                .build();

        streamBridge.send(binaryProcessingBinding, message);
        log.info("Sent a message {}", message);
    }
}
