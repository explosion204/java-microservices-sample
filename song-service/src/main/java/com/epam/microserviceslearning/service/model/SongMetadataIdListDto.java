package com.epam.microserviceslearning.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SongMetadataIdListDto {
    private List<Long> ids;
}
