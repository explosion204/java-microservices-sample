package com.epam.microserviceslearning.gateway.error.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class DefaultErrorHandler implements ErrorHandler {
    private static final String DEFAULT_ERROR_MESSAGE = "Something went unexpected on server-side";

    @Override
    public boolean supports(Map<String, Object> errorDetails) {
        return true;
    }

    @Override
    public Map<String, Object> handle(Throwable error, Map<String, Object> errorDetails) {
        log.error("Caught an exception", error);

        errorDetails.put(MESSAGE_ATTRIBUTE, DEFAULT_ERROR_MESSAGE);

        return errorDetails;
    }
}
