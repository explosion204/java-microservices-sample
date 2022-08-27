package com.epam.microserviceslearning.resource.controller.exception;

import com.epam.microserviceslearning.common.logging.LoggingService;
import com.epam.microserviceslearning.resource.exception.BinaryDeletedException;
import com.epam.microserviceslearning.resource.exception.BinaryNotFoundException;
import com.epam.microserviceslearning.resource.exception.UnsupportedBinaryTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String DEFAULT_ERROR_MESSAGE = "Something went unexpected on server-side";

    private final LoggingService logger;

    @ExceptionHandler(UnsupportedBinaryTypeException.class)
    public ResponseEntity<Object> handleUnsupportedBinaryTypeException(UnsupportedBinaryTypeException e) {
        final String errorMessage = String.format("Files of type '%s' are not supported", e.getInvalidType());
        logger.error(errorMessage);
        return buildErrorResponseEntity(BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        final String errorMessage = e.getMessage();
        logger.error(errorMessage);
        return buildErrorResponseEntity(BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(BinaryNotFoundException.class)
    public ResponseEntity<Object> handleBinaryNotFoundException(BinaryNotFoundException e) {
        final String errorMessage = String.format("Unable to find binary content with id = %s", e.getId());
        logger.error(errorMessage);
        return buildErrorResponseEntity(NOT_FOUND, errorMessage);
    }

    @ExceptionHandler(BinaryDeletedException.class)
    public ResponseEntity<Object> handleBinaryDeletedException(BinaryDeletedException e) {
        final String errorMessage = String.format("Binary content with id = %s is already deleted", e.getId());
        logger.error(errorMessage);
        return buildErrorResponseEntity(NOT_FOUND, errorMessage);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleDefault(Throwable e) {
        logger.error("Caught unexpected exception: %s", e.getMessage());
        return buildErrorResponseEntity(INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MESSAGE);
    }

    private ResponseEntity<Object> buildErrorResponseEntity(HttpStatus status, String errorMessage) {
        final Map<String, Object> body = new HashMap<>();
        body.put(ERROR_MESSAGE, errorMessage);

        return new ResponseEntity<>(body, status);
    }
}
