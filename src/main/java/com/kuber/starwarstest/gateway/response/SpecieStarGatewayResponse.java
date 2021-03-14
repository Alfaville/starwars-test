package com.kuber.starwarstest.gateway.response;

import com.kuber.starwarstest.controller.response.ResponseBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecieStarGatewayResponse implements ResponseBase {
    private String name;
    private String classification;
    private String designation;
    private String averageHeight;
    private String skinColors;
    private String hairColors;
    private String eyeColors;
    private String averageLifespan;
    private String language;
}
