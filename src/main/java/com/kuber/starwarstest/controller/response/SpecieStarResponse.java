package com.kuber.starwarstest.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class SpecieStarResponse implements ResponseBase {
    @Setter
    private Integer id;
    private String name;
    private String classification;
    private String designation;
    private String averageHeight;
    private String averageLifespan;
    private String language;
}
