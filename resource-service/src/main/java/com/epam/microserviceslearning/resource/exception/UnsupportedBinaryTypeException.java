package com.epam.microserviceslearning.resource.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Data
public class UnsupportedBinaryTypeException extends RuntimeException {
    private final String invalidType;
}
