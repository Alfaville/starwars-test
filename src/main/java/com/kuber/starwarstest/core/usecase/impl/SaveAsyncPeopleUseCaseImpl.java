package com.kuber.starwarstest.core.usecase.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuber.starwarstest.core.usecase.SaveAsyncPeopleUseCase;
import com.kuber.starwarstest.dataprovider.repository.PersonRepository;
import com.kuber.starwarstest.entrypoint.http.converter.PeopleResponseToPeopleEntityConverter;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveAsyncPeopleUseCaseImpl implements SaveAsyncPeopleUseCase {

    private final ObjectMapper objectMapper;
    private final PersonRepository personRepository;
    private final PeopleResponseToPeopleEntityConverter peopleResponseToPeopleEntityConverter;

    @RabbitListener(queues = "${queue.person-service}")
    public void execute(@Payload String payload) {
        try {
            log.info("Received {}", payload);
            var personResponse = objectMapper.readValue(payload, PeopleStarResponse.class);
            var personEntity = peopleResponseToPeopleEntityConverter.convert(personResponse);
            personRepository.save(personEntity);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

}