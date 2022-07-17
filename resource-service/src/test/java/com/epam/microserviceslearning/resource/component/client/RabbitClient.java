package com.epam.microserviceslearning.resource.component.client;

import com.epam.microserviceslearning.resource.component.client.model.BinaryObjectIdDto;
import com.google.gson.Gson;
import io.cucumber.spring.CucumberTestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Scope;

import java.nio.charset.StandardCharsets;

import static com.epam.microserviceslearning.resource.component.config.ResourceServiceTestConfiguration.QUEUE_NAME;


@TestComponent
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
@RequiredArgsConstructor
public class RabbitClient {
    private final Gson gson;
    private final RabbitTemplate rabbitTemplate;

    public long getId() {
        final byte[] payload = rabbitTemplate.receive(QUEUE_NAME).getBody();
        final String json = new String(payload, StandardCharsets.UTF_8);
        final BinaryObjectIdDto idDto = gson.fromJson(json, BinaryObjectIdDto.class);
        return idDto.getId();
    }
}
