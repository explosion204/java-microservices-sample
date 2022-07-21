package com.epam.microserviceslearning.song.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SongMetadataIdListDto {
    private List<Long> ids;
}
