package com.epam.microserviceslearning.controller.exception;

import com.epam.microserviceslearning.exception.MetadataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String DEFAULT_ERROR_MESSAGE = "Something went unexpected on server-side";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        final String errorMessage = e.getMessage();
        log.error(errorMessage);
        return buildErrorResponseEntity(BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleMissingMetadataException() {
        final String errorMessage = "Metadata body is invalid";
        log.error(errorMessage);
        return buildErrorResponseEntity(BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(MetadataNotFoundException.class)
    public ResponseEntity<Object> handleMetadataNotFoundException(MetadataNotFoundException e) {
        final String errorMessage = String.format("Unable to find song metadata with id = %s", e.getId());
        log.error(errorMessage);
        return buildErrorResponseEntity(NOT_FOUND, errorMessage);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleDefault(Throwable e) {
        log.error("Caught unexpected exception: {}", e.getMessage());
        return buildErrorResponseEntity(INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MESSAGE);
    }

    private ResponseEntity<Object> buildErrorResponseEntity(HttpStatus status, String errorMessage) {
        final Map<String, Object> body = new HashMap<>();
        body.put(ERROR_MESSAGE, errorMessage);

        return new ResponseEntity<>(body, status);
    }
}
