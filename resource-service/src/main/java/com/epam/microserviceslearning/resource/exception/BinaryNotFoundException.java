package com.epam.microserviceslearning.resource.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class BinaryNotFoundException extends RuntimeException {
    private final long id;
}
