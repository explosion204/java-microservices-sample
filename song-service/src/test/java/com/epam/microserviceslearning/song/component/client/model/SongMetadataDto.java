package com.epam.microserviceslearning.song.component.client.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SongMetadataDto {
    private long resourceId;
    private String name;
    private String artist;
    private String album;
    private String length;
    private int year;
}
