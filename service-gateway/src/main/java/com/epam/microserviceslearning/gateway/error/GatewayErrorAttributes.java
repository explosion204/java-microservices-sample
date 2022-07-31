package com.epam.microserviceslearning.gateway.error;

import com.epam.microserviceslearning.gateway.error.handler.ErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GatewayErrorAttributes extends DefaultErrorAttributes {
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String REQUEST_ID_ATTRIBUTE = "requestId";

    private final List<ErrorHandler> errorHandlers;

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        final Map<String, Object> details = super.getErrorAttributes(request, options);
        final Throwable error = super.getError(request);

        return errorHandlers.stream()
                .filter(handler -> handler.supports(details))
                .map(handler -> handler.handle(error, details))
                .map(this::clean)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to find proper error handler"));
    }

    private Map<String, Object> clean(Map<String, Object> response) {
        response.remove(ERROR_ATTRIBUTE);
        response.remove(REQUEST_ID_ATTRIBUTE);

        return response;
    }
}
