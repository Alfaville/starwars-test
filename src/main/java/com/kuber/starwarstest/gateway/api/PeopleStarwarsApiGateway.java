package com.kuber.starwarstest.gateway.api;

import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.controller.response.PeopleStarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "people-stars", url = "https://swapi.dev/api/")
public interface PeopleStarwarsApiGateway {

    @GetMapping(value = "/people/")
    PaginableResponse<PeopleStarResponse> getAllPeople();

}
