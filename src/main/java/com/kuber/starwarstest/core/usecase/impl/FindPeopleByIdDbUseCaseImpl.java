package com.kuber.starwarstest.core.usecase.impl;

import com.kuber.starwarstest.core.usecase.FindPeopleByIdUseCase;
import com.kuber.starwarstest.core.usecase.converter.PeopleEntityToPeopleResponseConverter;
import com.kuber.starwarstest.dataprovider.repository.PersonRepository;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("FindPeopleByIdDbUseCaseImpl")
@RequiredArgsConstructor
public class FindPeopleByIdDbUseCaseImpl implements FindPeopleByIdUseCase {

    private final PersonRepository personRepository;
    private final PeopleEntityToPeopleResponseConverter peopleEntityToPeopleResponseConverter;

    @Override
    public Optional<PeopleStarResponse> execute(Long id) {
        var personEntityOp = personRepository.findById(id);
        if (personEntityOp.isPresent()) {
            var personResponse = peopleEntityToPeopleResponseConverter.convert(personEntityOp.get());
            return Optional.of(personResponse);
        }
        return Optional.empty();
    }

}
