package com.kuber.starwarstest.core.usecase.impl;

import com.kuber.starwarstest.entrypoint.http.response.PaginableResponse;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.PlanetStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.SpecieStarResponse;
import com.kuber.starwarstest.dataprovider.api.StarwarsApiGateway;
import com.kuber.starwarstest.dataprovider.api.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.core.usecase.FindAllPeopleStarUseCase;
import com.kuber.starwarstest.core.usecase.converter.PeopleStarwarGatewayToPeopleResponseConverter;
import com.kuber.starwarstest.core.usecase.converter.PlanetStarwarGatewayToPlanetResponseConverter;
import com.kuber.starwarstest.core.usecase.converter.SpecieStarwarGatewayToSpecieResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.isNull;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Component
@RequiredArgsConstructor
public class FindAllPeopleStarUseCaseImpl implements FindAllPeopleStarUseCase {

    private final StarwarsApiGateway starwarsApiGateway;
    private final PeopleStarwarGatewayToPeopleResponseConverter peopleStarwarGatewayToPeopleResponseConverter;
    private final SpecieStarwarGatewayToSpecieResponseConverter specieStarwarGatewayToSpecieResponseConverter;
    private final PlanetStarwarGatewayToPlanetResponseConverter planetStarwarGatewayToPlanetResponseConverter;

    @Override
    public List<PeopleStarResponse> execute(Integer page) {
        final PaginableResponse<PeopleStarGatewayResponse> responseGateway = starwarsApiGateway.getAllPeoplePerPage(page);
        final List<PeopleStarResponse> peopleStarGatewayResponseList = peopleStarwarGatewayToPeopleResponseConverter.convert(responseGateway);
        peopleStarGatewayResponseList.stream().parallel().forEach(people -> {

            final CompletableFuture<SpecieStarResponse> specieRequestAsyn = getSpecieByIdAsync(people.getSpecie().getId());
            final CompletableFuture<PlanetStarResponse> planetRequestAsyn = getPlanetByIdAsync(people.getHomeworld().getId());

            allOf(specieRequestAsyn, planetRequestAsyn);

            people.setSpecie(specieRequestAsyn.join());
            people.setHomeworld(planetRequestAsyn.join());
        });
        return peopleStarGatewayResponseList;
    }

    private CompletableFuture<SpecieStarResponse> getSpecieByIdAsync(Integer id) {
        if(isNull(id)) {
            return CompletableFuture.completedFuture(null);
        } else {
            return supplyAsync(() -> {
                final SpecieStarResponse response = specieStarwarGatewayToSpecieResponseConverter.convert(starwarsApiGateway.getSpecieById(id));
                response.setId(id);
                return response;
            });
        }
    }

    private CompletableFuture<PlanetStarResponse> getPlanetByIdAsync(Integer id) {
        if(isNull(id)) {
            return CompletableFuture.completedFuture(null);
        } else {
            return supplyAsync(() -> {
                final PlanetStarResponse response = planetStarwarGatewayToPlanetResponseConverter.convert(starwarsApiGateway.getPlanetById(id));
                response.setId(id);
                return response;
            });
        }
    }

}
