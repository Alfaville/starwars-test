package com.kuber.starwarstest.core.usecase.converter;

import com.kuber.starwarstest.core.entity.PersonEntity;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Lazy
@Component
@RequiredArgsConstructor
public class PeopleEntityToPeopleResponseConverter implements Converter<PersonEntity, PeopleStarResponse> {

    private final SpecieEntityToSpecieResponseConverter specieEntityToSpecieResponseConverter;
    private final PlanetEntityToPlanetResponseConverter planetEntityToPlanetResponseConverter;

    @Override
    public PeopleStarResponse convert(PersonEntity source) {
        return PeopleStarResponse.builder()
                .id(source.getId())
                .birthYear(source.getBirthYear())
                .name(source.getName())
                .eyeColor(source.getEyeColor())
                .gender(source.getGender().name())
                .hairColor(source.getHairColor())
                .height(source.getHeight().toString())
                .mass(source.getMass().toString())
                .skinColor(source.getSkinColor())
                .specie(specieEntityToSpecieResponseConverter.convert(source.getSpecie()))
                .homeworld(planetEntityToPlanetResponseConverter.convert(source.getPlanet()))
                .build();
    }



}