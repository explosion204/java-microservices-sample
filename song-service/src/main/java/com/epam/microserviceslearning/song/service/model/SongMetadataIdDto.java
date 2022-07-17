package com.epam.microserviceslearning.song.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongMetadataIdDto {
    private long id;
}
