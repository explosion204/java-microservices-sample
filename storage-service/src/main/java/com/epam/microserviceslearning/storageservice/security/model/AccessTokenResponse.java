package com.epam.microserviceslearning.storageservice.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenResponse {
    private String accessToken;
}
