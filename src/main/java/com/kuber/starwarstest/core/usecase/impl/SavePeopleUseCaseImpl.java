package com.kuber.starwarstest.core.usecase.impl;

import com.kuber.starwarstest.core.usecase.SavePeopleUseCase;
import com.kuber.starwarstest.dataprovider.repository.PersonRepository;
import com.kuber.starwarstest.entrypoint.http.converter.PeopleResponseToPeopleEntityConverter;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavePeopleUseCaseImpl implements SavePeopleUseCase {

    private final PersonRepository personRepository;
    private final PeopleResponseToPeopleEntityConverter peopleResponseToPeopleEntityConverter;

    public void execute(PeopleStarResponse personResponse) {
        try {
            log.info("Calling savePeople method");
            var personEntity = peopleResponseToPeopleEntityConverter.convert(personResponse);
            personRepository.save(personEntity);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        }
    }

}
