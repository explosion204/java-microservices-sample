package com.epam.microserviceslearning.common.logging.appender;

import com.epam.microserviceslearning.common.logging.model.LogType;
import com.epam.microserviceslearning.common.logging.trace.TraceUtils;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.messaging.logs-binding")
@ConditionalOnClass(StreamBridge.class)
public class AmqpLogAppender implements LogAppender {
    private final StreamBridge streamBridge;
    private final Gson gson;

    @Value("${service.messaging.logs-binding}")
    private String logsBinding;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void append(LogType type, String message) {
        final LogModel model = buildLogModel(message, type);
        final String payload = gson.toJson(model);
        final Message<String> amqpMessage = MessageBuilder.withPayload(payload).build();

        streamBridge.send(logsBinding, amqpMessage);
    }

    private LogModel buildLogModel(String message, LogType type) {
        final String traceId = TraceUtils.getTraceId();
        return LogModel.builder()
                .traceId(traceId)
                .sender(applicationName)
                .type(type)
                .message(message)
                .build();
    }

    @Data
    @Builder
    private static class LogModel {
        private String traceId;
        private String sender;
        private LogType type;
        private String message;
    }
}
