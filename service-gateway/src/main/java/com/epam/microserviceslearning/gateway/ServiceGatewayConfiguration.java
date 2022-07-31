package com.epam.microserviceslearning.gateway;

import com.google.gson.Gson;
import org.apache.http.message.HeaderGroup;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class ServiceGatewayConfiguration {
    private static final String RESOURCE_SERVICE_URI = "lb://resource-service";
    private static final String SONG_SERVICE_URI = "lb://song-service";

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/resources/{id:\\d+}")
                        .and()
                        .method(GET)
                        .uri(RESOURCE_SERVICE_URI))
                .route(r -> r.path("/resources")
                        .and()
                        .method(POST)
                        .uri(RESOURCE_SERVICE_URI))
                .route(r -> r.path("/resources")
                        .and()
                        .method(DELETE)
                        .uri(RESOURCE_SERVICE_URI))
                .route(r -> r.path("/songs/{id:\\d+}")
                        .and()
                        .method(GET)
                        .uri(SONG_SERVICE_URI))
                .route(r -> r.path("/songs")
                        .and()
                        .method(POST)
                        .uri(SONG_SERVICE_URI))
                .route(r -> r.path("/songs")
                        .and()
                        .method(DELETE)
                        .uri(SONG_SERVICE_URI))
                .build();
    }
}
