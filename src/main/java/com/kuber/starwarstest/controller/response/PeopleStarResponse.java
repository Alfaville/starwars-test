package com.kuber.starwarstest.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeopleStarResponse implements ResponseBase {
    private String name;
    private String specie;
    private String mass;
    private String height;
    private String hairColor;
    private String eyeColor;
    private String skinColor;
    private String gender;
    private String birthYear;
    private String homeworld;
}
