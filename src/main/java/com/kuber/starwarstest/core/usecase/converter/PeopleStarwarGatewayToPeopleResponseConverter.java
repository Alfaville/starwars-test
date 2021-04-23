package com.kuber.starwarstest.core.usecase.converter;

import com.kuber.starwarstest.dataprovider.api.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.PlanetStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.SpecieStarResponse;
import com.kuber.starwarstest.utils.UtilsKt;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

import static java.util.Objects.isNull;

@Lazy
@Component
public class PeopleStarwarGatewayToPeopleResponseConverter implements Converter<PeopleStarGatewayResponse, PeopleStarResponse> {

    @Override
    public PeopleStarResponse convert(PeopleStarGatewayResponse source) {
        if(isNull(source)) {
            return null;
        }
        var species = source.getSpecies();
        if(CollectionUtils.isEmpty(species)){
            species = Collections.emptyList();
        }
        final String specie = species.stream().findFirst().orElse(null);
        return PeopleStarResponse.builder()
               .birthYear(source.getBirthYear())
               .eyeColor(source.getEyeColor())
               .gender(source.getGender())
               .name(source.getName())
               .hairColor(source.getHairColor())
               .height(source.getHeight())
               .mass(source.getMass())
               .skinColor(source.getSkinColor())
               .specie(
                       SpecieStarResponse.builder().id(UtilsKt.getId(specie)).build()
               )
               .homeworld(
                       PlanetStarResponse.builder().id(UtilsKt.getId(source.getHomeworld())).build()
               )
               .build();
    }

}