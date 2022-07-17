package com.epam.microserviceslearning.processor.component.hooks;

import io.cucumber.java.BeforeAll;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainersHooks {
    private static RabbitMQContainer rabbitContainer;

    @BeforeAll
    public static void init() {
        launchRabbit();
    }

    private static void launchRabbit() {
        rabbitContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq"))
                .withExposedPorts(5672);

        rabbitContainer.start();

        System.setProperty("spring.rabbitmq.host", rabbitContainer.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitContainer.getAmqpPort().toString());
    }
}
