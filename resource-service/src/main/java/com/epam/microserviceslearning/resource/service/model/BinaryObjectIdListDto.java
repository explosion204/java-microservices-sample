package com.epam.microserviceslearning.resource.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BinaryObjectIdListDto {
    private List<Long> ids;
}
