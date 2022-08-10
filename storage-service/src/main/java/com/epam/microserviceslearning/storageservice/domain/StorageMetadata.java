package com.epam.microserviceslearning.storageservice.domain;

import com.epam.microserviceslearning.common.storage.factory.StorageProvider;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import lombok.Data;

@Data
public class StorageMetadata {
    private long id;
    private StorageType type;
    private StorageProvider provider;
    private String descriptor;
}
