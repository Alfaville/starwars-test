package com.kuber.starwarstest.dataprovider.api.response;

import com.kuber.starwarstest.entrypoint.http.response.ResponseBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanetStarGatewayResponse implements ResponseBase {
    private String name;
    private String rotationPeriod;
    private String orbitalPeriod;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private String surfaceWater;
    private String population;
}
