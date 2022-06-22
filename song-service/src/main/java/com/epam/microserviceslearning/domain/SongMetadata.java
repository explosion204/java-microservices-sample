package com.epam.microserviceslearning.domain;

import lombok.Data;

@Data
public class SongMetadata {
    private long id;
    private long resourceId;
    private String name;
    private String artist;
    private String album;
    private String length;
    private int year;
}
