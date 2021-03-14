package com.kuber.starwarstest.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PlanetStarResponse implements ResponseBase {
    @Setter
    private Integer id;
    private String name;
    private String climate;
    private String gravity;
    private String terrain;
    private String population;
}
