package com.epam.microserviceslearning.storageservice.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StorageMetadataIdListDto {
    private List<Long> ids;
}
