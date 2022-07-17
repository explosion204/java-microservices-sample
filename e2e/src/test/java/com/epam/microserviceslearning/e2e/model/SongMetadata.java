package com.epam.microserviceslearning.e2e.model;

import lombok.Data;

@Data
public class SongMetadata {
    private long resourceId;
    private String name;
    private String artist;
    private String album;
    private String length;
    private int year;
}
