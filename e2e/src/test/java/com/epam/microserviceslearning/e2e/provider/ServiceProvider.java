package com.epam.microserviceslearning.e2e.provider;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.util.Optional;

public class ServiceProvider {
    private static final String URL_PATTERN = "http://%s:%s";
    private static final String RESOURCE_SERVICE_NAME = "resource-service";
    private static final String SONG_SERVICE_NAME = "song-service";
    private static final int RESOURCE_SERVICE_PORT = 8080;
    private static final int SONG_SERVICE_PORT = 8081;

    public void launchEnvironment() {
        final File composeFile = getComposeFile();

        final DockerComposeContainer testEnvironment = new DockerComposeContainer(composeFile)
                .withEnv("ENV_FILE", getEnvironmentVariable("ENV_FILE", ".env"))
                .withExposedService(RESOURCE_SERVICE_NAME, RESOURCE_SERVICE_PORT, buildHealthCheck())
                .withExposedService(SONG_SERVICE_NAME, SONG_SERVICE_PORT, buildHealthCheck());

        testEnvironment.start();
    }

    public String getResourceServiceUrl() {
        return String.format(URL_PATTERN, "localhost", RESOURCE_SERVICE_PORT);
    }

    public String getSongServiceUrl() {
        return String.format(URL_PATTERN, "localhost", SONG_SERVICE_PORT);
    }

    private File getComposeFile() {
        final ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("docker-compose.yaml").getFile());
    }

    private HttpWaitStrategy buildHealthCheck() {
        return Wait.forHttp("/")
                .forStatusCode(404);
    }

    private String getEnvironmentVariable(String key, String defaultValue) {
        return Optional.ofNullable(System.getenv(key))
                .orElse(defaultValue);
    }
}
