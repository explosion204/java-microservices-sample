package com.epam.microserviceslearning.processor;

import com.epam.microserviceslearning.common.logging.trace.FeignTraceInterceptor;
import com.google.gson.Gson;
import feign.RequestInterceptor;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ResourceProcessorConfiguration {
    private final RetryRegistry retryRegistry;

    @PostConstruct
    public void retryLogging() {
        retryRegistry.retry("save-metadata")
                .getEventPublisher()
                .onRetry(event -> log.warn("{}", event));
    }

    @Bean
    public Mp3Parser mp3Parser() {
        return new Mp3Parser();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public RequestInterceptor feignTraceInterceptor() {
        return new FeignTraceInterceptor();
    }
}
