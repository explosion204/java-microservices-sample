package com.epam.microserviceslearning.song.component.hooks;

import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainersHooks {
    private static PostgreSQLContainer<?> postgresContainer;

    @BeforeAll
    public static void init() {
        launchPostgres();
    }

    @After
    public void cleanupPostgres() {
        postgresContainer.stop();
        launchPostgres();
    }

    private static void launchPostgres() {
        postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres"))
                .withDatabaseName("test")
                .withUsername("postgres")
                .withPassword("postgres")
                .withExposedPorts(5432);

        postgresContainer.start();

        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }
}
