package com.epam.microserviceslearning.e2e.client;

import com.epam.microserviceslearning.e2e.model.SongMetadata;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class SongServiceClient extends AbstractServiceClient {
    public SongServiceClient(String baseUrl) {
        super(baseUrl);
    }

    @SneakyThrows
    public Optional<SongMetadata> getMetadata(long id) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(getBaseUrl() + "/songs/" + id))
                .GET()
                .build();

        final HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return Optional.empty();
        }

        final SongMetadata metadata = getGson().fromJson(response.body(), SongMetadata.class);
        return Optional.of(metadata);
    }
}
