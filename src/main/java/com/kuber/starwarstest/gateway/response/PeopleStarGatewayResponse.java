package com.kuber.starwarstest.gateway.response;

import com.kuber.starwarstest.controller.response.ResponseBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PeopleStarGatewayResponse implements ResponseBase {
    private String name;
    private String mass;
    private String height;
    private String hairColor;
    private String eyeColor;
    private String skinColor;
    private String gender;
    private String birthYear;
    private List<String> species;
    private String homeworld;
}
