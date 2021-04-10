package com.kuber.starwarstest.gateway.api;

import com.kuber.starwarstest.gateway.fallback.StarwarsApiGatewayFallback;
import com.kuber.starwarstest.gateway.response.PlanetStarGatewayResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${feign.swapi.planet}", url = "${feign.swapi.urlBase}", fallback = StarwarsApiGatewayFallback.class)
public interface PlanetStarwarsApiGateway {

    @GetMapping(value = "/planets/{id}/")
    PlanetStarGatewayResponse getPlanetById(@PathVariable(value = "id") Integer id);

}
