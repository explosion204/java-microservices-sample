package com.epam.microserviceslearning.processor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongMetadataDto {
    private long resourceId;
    private String name;
    private String artist;
    private String album;
    private String length;
    private int year;
}
