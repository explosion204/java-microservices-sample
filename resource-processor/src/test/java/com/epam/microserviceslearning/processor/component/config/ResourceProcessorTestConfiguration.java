package com.epam.microserviceslearning.processor.component.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.epam.microserviceslearning.processor" })
@TestPropertySource(value = "classpath:/application.yaml")
public class ResourceProcessorTestConfiguration {
    public static final String BINDING_NAME = "binary-processing";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(BINDING_NAME);
        rabbitTemplate.setRoutingKey(BINDING_NAME);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
}
