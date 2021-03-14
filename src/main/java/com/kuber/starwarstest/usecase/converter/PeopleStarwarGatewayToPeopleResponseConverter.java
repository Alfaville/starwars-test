package com.kuber.starwarstest.usecase.converter;

import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.controller.response.PeopleStarResponse;
import com.kuber.starwarstest.controller.response.PlanetStarResponse;
import com.kuber.starwarstest.controller.response.SpecieStarResponse;
import com.kuber.starwarstest.gateway.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.utils.UtilsKt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PeopleStarwarGatewayToPeopleResponseConverter implements Converter<PaginableResponse<PeopleStarGatewayResponse>, List<PeopleStarResponse>> {

    @Override
    public List<PeopleStarResponse> convert(PaginableResponse<PeopleStarGatewayResponse> source) {
        final List<PeopleStarResponse> peopleStarGatewayRespons = new ArrayList<>();
        source.getResults().forEach(dto -> {
            var species = dto.getSpecies();
            if(CollectionUtils.isEmpty(species)){
                species = Collections.emptyList();
            }
            final String specie = species.stream().findFirst().orElse(null);
            peopleStarGatewayRespons.add(
                    PeopleStarResponse.builder()
                            .birthYear(dto.getBirthYear())
                            .eyeColor(dto.getEyeColor())
                            .gender(dto.getGender())
                            .hairColor(dto.getHairColor())
                            .height(dto.getHeight())
                            .mass(dto.getMass())
                            .name(dto.getName())
                            .skinColor(dto.getSkinColor())
                            .specie(
                                    SpecieStarResponse.builder().id(UtilsKt.getId(specie)).build()
                            )
                            .homeworld(
                                    PlanetStarResponse.builder().id(UtilsKt.getId(dto.getHomeworld())).build()
                            )
                            .build()
            );
        });
        return peopleStarGatewayRespons;
    }

}