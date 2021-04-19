package com.kuber.starwarstest.core.usecase.converter;

import com.kuber.starwarstest.entrypoint.http.response.PlanetStarResponse;
import com.kuber.starwarstest.dataprovider.api.response.PlanetStarGatewayResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlanetStarwarGatewayToPlanetResponseConverter implements Converter<PlanetStarGatewayResponse, PlanetStarResponse> {

    @Override
    public PlanetStarResponse convert(PlanetStarGatewayResponse source) {
        return PlanetStarResponse.builder()
                .climate(source.getClimate())
                .name(source.getName())
                .gravity(source.getGravity())
                .population(source.getPopulation())
                .terrain(source.getTerrain())
                .build();
    }

}