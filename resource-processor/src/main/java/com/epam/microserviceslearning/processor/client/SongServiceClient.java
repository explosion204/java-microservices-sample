package com.epam.microserviceslearning.processor.client;

import com.epam.microserviceslearning.processor.model.SongMetadataDto;
import com.epam.microserviceslearning.processor.model.IdDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "song-service-client", url = "${service-gateway.url}")
public interface SongServiceClient {
    @PostMapping(value = "/songs")
    @Retry(name = "rest-retry")
    IdDto saveMetadata(@RequestBody SongMetadataDto metadata);
}