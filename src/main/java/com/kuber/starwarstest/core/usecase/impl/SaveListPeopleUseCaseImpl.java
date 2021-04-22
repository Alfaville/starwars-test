package com.kuber.starwarstest.core.usecase.impl;

import com.kuber.starwarstest.core.usecase.SaveListPeopleUseCase;
import com.kuber.starwarstest.dataprovider.repository.PersonRepository;
import com.kuber.starwarstest.entrypoint.http.converter.ListPeopleResponseToListPeopleEntityConverter;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveListPeopleUseCaseImpl implements SaveListPeopleUseCase {

    private final PersonRepository personRepository;
    private final ListPeopleResponseToListPeopleEntityConverter listPeopleResponseToListPeopleEntityConverter;

    public void execute(List<PeopleStarResponse> listPersonResponse) {
        try {
            log.info("Calling savePeople method");
            var listPersonEntity = listPeopleResponseToListPeopleEntityConverter.convert(listPersonResponse);

            personRepository.saveAll(
                    StreamSupport.stream(listPersonEntity.spliterator(), false)
                    .collect(Collectors.toList())
            );
        } catch (Exception e) {
//            TODO: change exception from one more specific
            log.error("ERROR: {}", e.getMessage());
        }
    }

}
