package com.kuber.starwarstest.usecase.converter;

import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.controller.response.PeopleStarResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleStarwarGatewayToPeopleResponseConverter implements Converter<PaginableResponse<PeopleStarResponse>, List<PeopleStarResponse>> {

    @Override
    public List<PeopleStarResponse> convert(PaginableResponse<PeopleStarResponse> source) {
        final List<PeopleStarResponse> peopleStarResponses = new ArrayList<>();
        source.getResults().forEach(dto ->
            peopleStarResponses.add(
                    PeopleStarResponse.builder()
                            .birthYear(dto.getBirthYear())
                            .eyeColor(dto.getEyeColor())
                            .gender(dto.getGender())
                            .hairColor(dto.getHairColor())
                            .height(dto.getHeight())
                            .homeworld(dto.getHomeworld())
                            .mass(dto.getMass())
                            .name(dto.getName())
                            .skinColor(dto.getSkinColor())
                            .specie(dto.getSpecie())
                            .build()
            )
        );
        return peopleStarResponses;
    }
}