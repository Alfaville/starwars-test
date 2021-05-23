package com.kuber.starwarstest.core.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuber.starwarstest.config.property.PersonQueueProperty;
import com.kuber.starwarstest.core.usecase.PutPeopleQueueUseCase;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PutPeopleQueueUseCaseImpl implements PutPeopleQueueUseCase {

    private final PersonQueueProperty personQueueProperty;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Boolean execute(PeopleStarResponse peopleStarResponse) {
        try {
            String message = objectMapper.writeValueAsString(peopleStarResponse);
            log.debug(message);
            rabbitTemplate.convertAndSend(personQueueProperty.getPersonService(), message);
            return Boolean.TRUE;
        } catch (JsonProcessingException e) {
            log.error("Error parse");
            return Boolean.FALSE;
        }
    }

}
