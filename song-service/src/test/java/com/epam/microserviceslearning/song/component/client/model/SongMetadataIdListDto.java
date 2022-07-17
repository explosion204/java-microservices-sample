package com.epam.microserviceslearning.song.component.client.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SongMetadataIdListDto {
    private List<Long> ids;
}
