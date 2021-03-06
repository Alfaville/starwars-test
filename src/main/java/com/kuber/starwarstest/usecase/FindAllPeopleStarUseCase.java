package com.kuber.starwarstest.usecase;

import com.kuber.starwarstest.controller.response.PeopleStarResponse;

import java.util.List;

public interface FindAllPeopleStarUseCase {

    List<PeopleStarResponse> execute();

}
