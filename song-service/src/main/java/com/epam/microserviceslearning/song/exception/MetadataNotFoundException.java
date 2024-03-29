package com.epam.microserviceslearning.song.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class MetadataNotFoundException extends RuntimeException {
    private final long id;
}
