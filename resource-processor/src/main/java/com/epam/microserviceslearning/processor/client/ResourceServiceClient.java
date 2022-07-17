package com.epam.microserviceslearning.processor.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "resource-service-client", url = "${resource-service.url}")
public interface ResourceServiceClient {
    @GetMapping(value = "/resources/{id}")
    @Retry(name = "download-file")
    byte[] downloadFile(@PathVariable("id") long binaryId);
}
