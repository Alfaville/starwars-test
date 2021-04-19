package com.kuber.starwarstest.dataprovider.api;

import com.kuber.starwarstest.entrypoint.http.response.PaginableResponse;
import com.kuber.starwarstest.dataprovider.fallback.StarwarsApiGatewayFallback;
import com.kuber.starwarstest.dataprovider.api.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.dataprovider.api.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.dataprovider.api.response.SpecieStarGatewayResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${feign.swapi.nameService}", url = "${feign.swapi.urlBase}", fallback = StarwarsApiGatewayFallback.class)
public interface StarwarsApiGateway {

    @GetMapping(value = "/people/", params = "page")
    PaginableResponse<PeopleStarGatewayResponse> getAllPeoplePerPage(@RequestParam(value = "page") Integer page);

    @GetMapping(value = "/species/{id}/")
    SpecieStarGatewayResponse getSpecieById(@PathVariable(value = "id") Integer id);

    @GetMapping(value = "/planets/{id}/")
    PlanetStarGatewayResponse getPlanetById(@PathVariable(value = "id") Integer id);

}
