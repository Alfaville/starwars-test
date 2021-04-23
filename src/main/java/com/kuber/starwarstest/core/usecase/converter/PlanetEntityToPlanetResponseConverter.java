package com.kuber.starwarstest.core.usecase.converter;

import com.kuber.starwarstest.core.entity.PlanetEntity;
import com.kuber.starwarstest.dataprovider.api.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.entrypoint.http.response.PlanetStarResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Lazy
@Component
public class PlanetEntityToPlanetResponseConverter implements Converter<PlanetEntity, PlanetStarResponse> {

    @Override
    public PlanetStarResponse convert(PlanetEntity source) {
        if(isNull(source)) {
            return null;
        }
        return PlanetStarResponse.builder()
                .id(source.getId().intValue())
                .climate(source.getClimate())
                .name(source.getName())
                .gravity(source.getGravity())
                .population(source.getPopulation())
                .terrain(source.getTerrain())
                .build();
    }

}