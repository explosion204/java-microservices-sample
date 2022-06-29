package com.epam.microserviceslearning.service.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SongMetadataDto {
    private long resourceId;
    @NotNull private String name;
    @NotNull private String artist;
    @NotNull private String album;
    @NotNull private String length;
    private int year;
}
