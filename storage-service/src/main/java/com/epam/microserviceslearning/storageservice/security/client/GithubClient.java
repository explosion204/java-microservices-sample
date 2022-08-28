package com.epam.microserviceslearning.storageservice.security.client;

import com.epam.microserviceslearning.common.logging.LoggingService;
import com.epam.microserviceslearning.storageservice.security.model.GithubUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GithubClient {
    private final LoggingService logger;
    private final GithubApiFeignClient apiClient;
    private final GithubAuthFeignClient authClient;

    @Value("${service.auth.client-id}")
    private String clientId;

    @Value("${service.auth.client-secret}")
    private String clientSecret;

    @Value("${service.auth.redirect-uri}")
    private String redirectUri;

    public Optional<String> getApiKey(String authorizationCode) {
        String apiKey = null;

        try {
            final Map<String, Object> response = authClient.getApiKey(clientId, clientSecret, authorizationCode, redirectUri);
            apiKey = (String) response.get("access_token");
        } catch (Exception e) {
            logger.error("Unable to exchange authorization code for access token. Cause: %s", e.getMessage());
        }

        return Optional.ofNullable(apiKey);
    }

    public GithubUser getUser(String apiKey) {
        return apiClient.getUser(apiKey);
    }
}
