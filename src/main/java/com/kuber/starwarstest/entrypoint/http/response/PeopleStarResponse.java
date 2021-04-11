package com.kuber.starwarstest.entrypoint.http.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PeopleStarResponse implements ResponseBase {
    private String name;
    private String mass;
    private String height;
    private String hairColor;
    private String eyeColor;
    private String skinColor;
    private String gender;
    private String birthYear;
    @Setter
    private SpecieStarResponse specie;
    @Setter
    private PlanetStarResponse homeworld;
}
