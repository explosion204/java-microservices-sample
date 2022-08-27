package com.epam.microserviceslearning.common.logging.trace;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.epam.microserviceslearning.common.logging.trace.TraceUtils.TRACE_ID_HEADER;

@Component
public class AmqpTraceUtils {
    public <T> Consumer<Message<T>> buildProxy(Consumer<Message<T>> handler) {
        return message -> {
            final String traceId = (String) message.getHeaders().get(TRACE_ID_HEADER);
            TraceUtils.setTraceId(traceId);
            handler.accept(message);
        };
    }

    public <T> Message<T> postProcessMessage(Message<T> message) {
        final String traceId = TraceUtils.getTraceId();
        return MessageBuilder.fromMessage(message)
                .setHeader(TRACE_ID_HEADER, traceId)
                .build();
    }
}
