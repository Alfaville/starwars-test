package com.kuber.starwarstest.core.usecase;

import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;

import java.util.List;
import java.util.Optional;

public interface SaveOrFindPeopleByIdUseCase {

    Optional<PeopleStarResponse> execute(Long id);

}
