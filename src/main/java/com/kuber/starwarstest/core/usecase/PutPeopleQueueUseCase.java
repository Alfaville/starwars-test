package com.kuber.starwarstest.core.usecase;

import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;

public interface PutPeopleQueueUseCase {

    Boolean execute(PeopleStarResponse peopleStarResponse);

}
