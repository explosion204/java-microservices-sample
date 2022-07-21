package com.epam.microserviceslearning.e2e.client;

import com.epam.microserviceslearning.e2e.model.BinaryObjectId;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ResourceServiceClient extends AbstractServiceClient {
    public ResourceServiceClient(String baseUrl) {
        super(baseUrl);
    }

    @SneakyThrows
    public BinaryObjectId upload(byte[] data) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(getBaseUrl() + "/resources"))
                .header("Content-Type", "audio/mpeg")
                .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                .build();

        final String response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        return getGson().fromJson(response, BinaryObjectId.class);
    }

    @SneakyThrows
    public Optional<byte[]> download(long id) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(getBaseUrl() + "/resources/" + id))
                .GET()
                .build();

        final HttpResponse<byte[]> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() != 200) {
            return Optional.empty();
        }

        return Optional.of(response.body());
    }
}
