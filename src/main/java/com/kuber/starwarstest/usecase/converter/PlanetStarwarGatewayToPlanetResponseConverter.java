package com.kuber.starwarstest.usecase.converter;

import com.kuber.starwarstest.controller.response.PlanetStarResponse;
import com.kuber.starwarstest.gateway.response.PlanetStarGatewayResponse;
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