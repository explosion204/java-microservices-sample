package com.epam.microserviceslearning.storageservice.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "github-auth-client", url = "https://github.com")
public interface GithubAuthFeignClient {
    @GetMapping(value = "/login/oauth/access_token", produces = "application/json")
    Map<String, Object> getApiKey(@RequestParam("client_id") String clientId,
                                  @RequestParam("client_secret") String clientSecret,
                                  @RequestParam("code") String authorizationCode,
                                  @RequestParam("redirect_uri") String redirectUri);
}
