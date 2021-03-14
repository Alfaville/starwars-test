package com.kuber.starwarstest.gateway.api;

import com.kuber.starwarstest.gateway.fallback.StarwarsApiGatewayFallback;
import com.kuber.starwarstest.gateway.response.SpecieStarGatewayResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${feign.swapi.specie}", url = "${feign.swapi.urlBase}", fallback = StarwarsApiGatewayFallback.class)
public interface SpecieStarwarsApiGateway {

    @GetMapping(value = "/speciess/{id}/")
    SpecieStarGatewayResponse getSpecieById(@PathVariable(value = "id") Integer id);

}
