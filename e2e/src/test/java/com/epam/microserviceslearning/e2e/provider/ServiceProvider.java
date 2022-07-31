package com.epam.microserviceslearning.e2e.provider;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.util.Optional;

public class ServiceProvider {
    private static final String URL_PATTERN = "http://%s:%s";
    private static final String SERVICE_GATEWAY_NAME = "service-gateway";
    private static final String SERVICE_REGISTRY_NAME = "service-registry";
    private static final int SERVICE_GATEWAY_PORT = 8090;
    private static final int SERVICE_REGISTRY_PORT = 8761;

    public void launchEnvironment() {
        final File composeFile = getComposeFile();

        final DockerComposeContainer testEnvironment = new DockerComposeContainer(composeFile)
                .withEnv("ENV_FILE", getEnvironmentVariable("ENV_FILE", ".env"))
                .withExposedService(SERVICE_REGISTRY_NAME, SERVICE_REGISTRY_PORT, buildHealthCheck("/", 200))
                .withExposedService(SERVICE_GATEWAY_NAME, SERVICE_GATEWAY_PORT, buildHealthCheck("/resources/0", 404))
                .withExposedService(SERVICE_GATEWAY_NAME, SERVICE_GATEWAY_PORT, buildHealthCheck("/songs/0", 404));

        testEnvironment.start();
    }

    public String getServiceGatewayUrl() {
        return String.format(URL_PATTERN, "localhost", SERVICE_GATEWAY_PORT);
    }

    private File getComposeFile() {
        final ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("docker-compose.yaml").getFile());
    }

    private HttpWaitStrategy buildHealthCheck(String url, int statusCode) {
        return Wait.forHttp(url)
                .forStatusCode(statusCode);
    }


    private String getEnvironmentVariable(String key, String defaultValue) {
        return Optional.ofNullable(System.getenv(key))
                .orElse(defaultValue);
    }
}
