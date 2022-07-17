package com.epam.microserviceslearning.resource.component.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.epam.microserviceslearning.resource" })
@TestPropertySource(value = "classpath:/application.yaml")
public class ResourceServiceTestConfiguration {
    public static final String BINDING_NAME = "binary-processing";
    public static final String QUEUE_NAME = "queue";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RabbitAdmin rabbitAdmin) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(BINDING_NAME);
        rabbitTemplate.setRoutingKey(BINDING_NAME);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        queueAndExchangeSetup(rabbitAdmin);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    private void queueAndExchangeSetup(RabbitAdmin rabbitAdmin) {
        final Queue queue = new Queue(QUEUE_NAME, false);
        rabbitAdmin.declareQueue(queue);
        final TopicExchange exchange = new TopicExchange(BINDING_NAME);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue)
                .to(exchange)
                .with(BINDING_NAME));
    }
}
