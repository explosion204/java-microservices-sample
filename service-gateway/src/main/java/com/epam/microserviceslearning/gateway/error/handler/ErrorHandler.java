package com.epam.microserviceslearning.gateway.error.handler;

import java.util.Map;

public interface ErrorHandler {
    String MESSAGE_ATTRIBUTE = "message";
    String STATUS_CODE = "status";

    boolean supports(Map<String, Object> errorDetails);
    Map<String, Object> handle(Throwable error, Map<String, Object> errorDetails);
}
