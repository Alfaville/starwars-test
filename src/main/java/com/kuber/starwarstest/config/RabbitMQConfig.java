package com.kuber.starwarstest.config;

import com.kuber.starwarstest.config.property.PersonQueueProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
class RabbitMQConfig {

    private final PersonQueueProperty personQueueProperty;

    @Bean
    Queue queue() {
        return new Queue(personQueueProperty.getPersonService());
    }

}
