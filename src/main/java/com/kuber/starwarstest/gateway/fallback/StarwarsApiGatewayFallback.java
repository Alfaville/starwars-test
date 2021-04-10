package com.kuber.starwarstest.gateway.fallback;

import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.gateway.api.PeopleStarwarsApiGateway;
import com.kuber.starwarstest.gateway.api.PlanetStarwarsApiGateway;
import com.kuber.starwarstest.gateway.api.SpecieStarwarsApiGateway;
import com.kuber.starwarstest.gateway.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.gateway.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.gateway.response.SpecieStarGatewayResponse;
import org.springframework.stereotype.Component;

@Component
public class StarwarsApiGatewayFallback implements PeopleStarwarsApiGateway, PlanetStarwarsApiGateway, SpecieStarwarsApiGateway {

    @Override
    public PaginableResponse<PeopleStarGatewayResponse> getAllPeoplePerPage(Integer page) {
        return new PaginableResponse<>();
    }

    @Override
    public PlanetStarGatewayResponse getPlanetById(Integer id) {
        return new PlanetStarGatewayResponse();
    }

    @Override
    public SpecieStarGatewayResponse getSpecieById(Integer id) {
        return new SpecieStarGatewayResponse();
    }

}
