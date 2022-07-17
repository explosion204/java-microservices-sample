package com.epam.microserviceslearning.e2e.client;

import com.google.gson.Gson;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Data
@Slf4j
public abstract class AbstractServiceClient {
    private final HttpClient httpClient;
    private final Gson gson;
    private final String baseUrl;

    public AbstractServiceClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @SneakyThrows
    public boolean ping() {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(baseUrl))
                .GET()
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException e) {
            log.error("An error occurred: ", e);
            return false;
        }

        return true;
    }
}
