package com.epam.microserviceslearning.resource.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BinaryObject {
    private long id;
    private long storageId;
    private String filename;
    private Status status;

    public enum Status {
        SUCCESS,
        FAILED,
        DELETED
    }
}
