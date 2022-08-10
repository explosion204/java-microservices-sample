package com.epam.microserviceslearning.resource.service.model;

import com.epam.microserviceslearning.common.storage.factory.StorageProvider;
import lombok.Data;

@Data
public class StorageMetadataDto {
    private long id;
    private StorageProvider provider;
    private String descriptor;
}
