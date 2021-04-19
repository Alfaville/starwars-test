package com.kuber.starwarstest.dataprovider.fallback;

import com.kuber.starwarstest.entrypoint.http.response.PaginableResponse;
import com.kuber.starwarstest.dataprovider.api.StarwarsApiGateway;
import com.kuber.starwarstest.dataprovider.api.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.dataprovider.api.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.dataprovider.api.response.SpecieStarGatewayResponse;
import org.springframework.stereotype.Component;

@Component
public class StarwarsApiGatewayFallback implements StarwarsApiGateway {

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
