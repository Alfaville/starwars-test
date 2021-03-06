package com.kuber.starwarstest.usecase.impl;

import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.controller.response.PeopleStarResponse;
import com.kuber.starwarstest.gateway.api.PeopleStarwarsApiGateway;
import com.kuber.starwarstest.usecase.FindAllPeopleStarUseCase;
import com.kuber.starwarstest.usecase.converter.PeopleStarwarGatewayToPeopleResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllPeopleStarUseCaseImpl implements FindAllPeopleStarUseCase {

    private final PeopleStarwarsApiGateway peopleStarwarsApiGateway;
    private final PeopleStarwarGatewayToPeopleResponseConverter peopleStarwarGatewayToPeopleResponseConverter;

    @Override
    public List<PeopleStarResponse> execute() {
        final PaginableResponse<PeopleStarResponse> responseGateway = peopleStarwarsApiGateway.getAllPeople();
        return peopleStarwarGatewayToPeopleResponseConverter.convert(responseGateway);
    }

}
