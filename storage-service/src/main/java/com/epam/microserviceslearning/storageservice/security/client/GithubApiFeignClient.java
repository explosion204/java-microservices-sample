package com.epam.microserviceslearning.storageservice.security.client;

import com.epam.microserviceslearning.storageservice.security.model.GithubUser;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface GithubApiFeignClient {
    @RequestLine("GET /user")
    @Headers("Authorization: Bearer {api_key}")
    GithubUser getUser(@Param("api_key") String apiKey);
}
