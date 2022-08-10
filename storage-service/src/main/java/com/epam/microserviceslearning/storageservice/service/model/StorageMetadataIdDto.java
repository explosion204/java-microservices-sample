package com.epam.microserviceslearning.storageservice.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StorageMetadataIdDto {
    private long id;
}
