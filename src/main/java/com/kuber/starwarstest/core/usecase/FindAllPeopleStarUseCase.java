package com.kuber.starwarstest.core.usecase;

import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;

import java.util.List;

public interface FindAllPeopleStarUseCase {

    List<PeopleStarResponse> execute(Integer page);

}
