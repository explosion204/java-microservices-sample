package com.epam.microserviceslearning.processor.component.client;

import com.epam.microserviceslearning.processor.model.IdDto;
import io.cucumber.spring.CucumberTestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Scope;

import static com.epam.microserviceslearning.processor.component.config.ResourceProcessorTestConfiguration.BINDING_NAME;

@TestComponent
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
@RequiredArgsConstructor
public class RabbitClient {
    private final RabbitTemplate rabbitTemplate;

    public void send(IdDto idDto) {
        rabbitTemplate.convertAndSend(BINDING_NAME, BINDING_NAME, idDto);
    }
}
