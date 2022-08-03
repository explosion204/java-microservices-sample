package com.epam.microserviceslearning.gateway.error.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Order(1)
public class NotFoundErrorHandler implements ErrorHandler {
    private static final String NOT_FOUND_ERROR_MESSAGE = "Route cannot be found";

    @Override
    public boolean supports(Map<String, Object> errorDetails) {
        final int statusCode = (int) errorDetails.get(STATUS_CODE);
        return statusCode == HttpStatus.NOT_FOUND.value();
    }

    @Override
    public Map<String, Object> handle(Throwable error, Map<String, Object> errorDetails) {
        errorDetails.put(MESSAGE_ATTRIBUTE, NOT_FOUND_ERROR_MESSAGE);
        return errorDetails;
    }
}
