package com.epam.microserviceslearning.resource.component.hooks;

import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

public class TestContainersHooks {
    private static LocalStackContainer localStackContainer;
    private static PostgreSQLContainer<?> postgresContainer;
    private static RabbitMQContainer rabbitContainer;

    @BeforeAll
    public static void init() {
        launchLocalStack();
        launchPostgres();
        launchRabbit();
    }

    @After
    public void cleanupPostgres() {
        postgresContainer.stop();
        launchPostgres();
    }

    private static void launchLocalStack() {
        localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
                .withServices(S3)
                .withExposedPorts(4566);

        localStackContainer.start();
    }

    private static void launchPostgres() {
        postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4-alpine"))
                .withDatabaseName("test")
                .withUsername("postgres")
                .withPassword("postgres")
                .withExposedPorts(5432);

        postgresContainer.start();

        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    private static void launchRabbit() {
        rabbitContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.10.6-alpine"))
                .withExposedPorts(5672);

        rabbitContainer.start();

        System.setProperty("spring.rabbitmq.host", rabbitContainer.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitContainer.getAmqpPort().toString());
    }
}
