package com.kuber.starwarstest.core.usecase.impl;

import com.kuber.starwarstest.core.entity.PersonEntity;
import com.kuber.starwarstest.core.usecase.SavePeopleUseCase;
import com.kuber.starwarstest.dataprovider.repository.PersonRepository;
import com.kuber.starwarstest.entrypoint.http.response.ResponseBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SavePeopleUseCaseImpl implements SavePeopleUseCase<T extends ResponseBase> {

    private final PersonRepository personRepository;

    public void execute(PersonEntity personResponse) {
        try {
            personRepository.save(T personResponse);
        } catch (Exception e) {
//            TODO: change exception from one more specific
            log.error("ERROR: {}", e.getMessage());
        }
    }

}
