package com.epam.microserviceslearning.storageservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class StorageNotFoundException extends RuntimeException {
    private final long id;
}
