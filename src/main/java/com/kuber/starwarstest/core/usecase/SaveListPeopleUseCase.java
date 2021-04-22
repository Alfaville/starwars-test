package com.kuber.starwarstest.core.usecase;

import com.kuber.starwarstest.core.entity.PersonEntity;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;

import java.util.List;

public interface SavePeopleUseCase {

    void execute(PersonEntity person);

}
