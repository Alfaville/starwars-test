package com.kuber.starwarstest.core.usecase.impl;

import com.kuber.starwarstest.core.usecase.FindPeopleByIdUseCase;
import com.kuber.starwarstest.core.usecase.SavePeopleUseCase;
import com.kuber.starwarstest.core.usecase.converter.PeopleStarwarGatewayToPeopleResponseConverter;
import com.kuber.starwarstest.core.usecase.converter.PlanetStarwarGatewayToPlanetResponseConverter;
import com.kuber.starwarstest.core.usecase.converter.SpecieStarwarGatewayToSpecieResponseConverter;
import com.kuber.starwarstest.dataprovider.api.StarwarsApiGateway;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.PlanetStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.SpecieStarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.isNull;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service("FindPeopleByIdExternalApiUseCaseImpl")
@RequiredArgsConstructor
public class FindPeopleByIdExternalApiUseCaseImpl implements FindPeopleByIdUseCase {

    private final StarwarsApiGateway starwarsApiGateway;
    private final SavePeopleUseCase savePeopleUseCase;
    private final PeopleStarwarGatewayToPeopleResponseConverter peopleStarwarGatewayToPeopleResponseConverter;
    private final SpecieStarwarGatewayToSpecieResponseConverter specieStarwarGatewayToSpecieResponseConverter;
    private final PlanetStarwarGatewayToPlanetResponseConverter planetStarwarGatewayToPlanetResponseConverter;

    @Override
    public Optional<PeopleStarResponse> execute(Long id) {
        Optional<PeopleStarResponse> response = getPeopleByIdInExternalApi(id);
        if (response.isPresent()) {
            runAsync(() -> savePeopleUseCase.execute(response.get()));
        }
        return response;
    }

    private Optional<PeopleStarResponse> getPeopleByIdInExternalApi(Long id) {
        var peopleStarwarGatewayResponse = starwarsApiGateway.getPeopleById(id);
        var peopleStarwarResponse = peopleStarwarGatewayToPeopleResponseConverter.convert(peopleStarwarGatewayResponse);

        if (isNull(peopleStarwarResponse)) {
            return Optional.empty();
        } else {
            var specieRequestAsync = getSpecieByIdAsync(peopleStarwarResponse.getSpecie().getId());
            var planetRequestAsync = getPlanetByIdAsync(peopleStarwarResponse.getHomeworld().getId());

            allOf(specieRequestAsync, planetRequestAsync);

            peopleStarwarResponse.setId(id);
            peopleStarwarResponse.setSpecie(specieRequestAsync.join());
            peopleStarwarResponse.setHomeworld(planetRequestAsync.join());

            return Optional.of(peopleStarwarResponse);
        }
    }

    private CompletableFuture<SpecieStarResponse> getSpecieByIdAsync(Integer id) {
        if (isNull(id)) {
            return completedFuture(null);
        } else {
            return supplyAsync(() -> {
                final SpecieStarResponse response = specieStarwarGatewayToSpecieResponseConverter.convert(starwarsApiGateway.getSpecieById(id));
                response.setId(id);
                return response;
            });
        }
    }

    private CompletableFuture<PlanetStarResponse> getPlanetByIdAsync(Integer id) {
        if (isNull(id)) {
            return completedFuture(null);
        } else {
            return supplyAsync(() -> {
                final PlanetStarResponse response = planetStarwarGatewayToPlanetResponseConverter.convert(starwarsApiGateway.getPlanetById(id));
                response.setId(id);
                return response;
            });
        }
    }

}
