package com.epam.microserviceslearning.processor.client;

import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.processor.model.IdDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "resource-service-client", url = "${service-gateway.url}")
public interface ResourceServiceClient {
    @GetMapping(value = "/resources/{id}")
    @Retry(name = "rest-retry")
    @CircuitBreaker(name = "rest-circuit-breaker")
    byte[] downloadFile(@PathVariable("id") long binaryId);

    @PostMapping(path = "/resources", consumes = "audio/mpeg")
    @Retry(name = "rest-retry")
    @CircuitBreaker(name = "rest-circuit-breaker")
    IdDto uploadFile(@RequestBody byte[] fileBytes, @RequestParam("storageType") StorageType storageType);

    @DeleteMapping(value = "/resources")
    @Retry(name = "rest-retry")
    @CircuitBreaker(name = "rest-circuit-breaker")
    void deleteFile(@RequestParam("id") long id);
}
