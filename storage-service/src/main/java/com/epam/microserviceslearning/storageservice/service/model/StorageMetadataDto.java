package com.epam.microserviceslearning.storageservice.service.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class StorageMetadataDto {
    private long id;

    @NotNull
    @Pattern(regexp = "PERMANENT|STAGING", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String type;

    @NotNull
    @Pattern(regexp = "S3|LOCAL", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String provider;

    @NotNull
    private String descriptor;
}
