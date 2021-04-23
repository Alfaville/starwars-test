package com.kuber.starwarstest.core.usecase.converter;

import com.kuber.starwarstest.entrypoint.http.response.PaginableResponse;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.PlanetStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.SpecieStarResponse;
import com.kuber.starwarstest.dataprovider.api.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.utils.UtilsKt;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Lazy
@Component
public class PeopleStarwarListGatewayToPeopleResponseListConverter implements Converter<PaginableResponse<PeopleStarGatewayResponse>, List<PeopleStarResponse>> {

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