package com.epam.microserviceslearning.resource.component.client.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BinaryObjectIdListDto {
    private List<Long> ids;
}
