package com.kuber.starwarstest.gateway.api;

import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.gateway.fallback.StarwarsApiGatewayFallback;
import com.kuber.starwarstest.gateway.response.PeopleStarGatewayResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${feign.swapi.people}", url = "${feign.swapi.urlBase}", fallback = StarwarsApiGatewayFallback.class)
public interface PeopleStarwarsApiGateway {

    @GetMapping(value = "/people/", params = "page")
    PaginableResponse<PeopleStarGatewayResponse> getAllPeoplePerPage(@RequestParam(value = "page") Integer page);

}
