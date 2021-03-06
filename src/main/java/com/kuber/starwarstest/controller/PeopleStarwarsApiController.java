package com.kuber.starwarstest.controller;

import com.kuber.starwarstest.controller.openapi.PeopleStarwarsOpenApi;
import com.kuber.starwarstest.controller.response.PeopleStarResponse;
import com.kuber.starwarstest.usecase.FindAllPeopleStarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/swapi/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PeopleStarwarsApiController implements PeopleStarwarsOpenApi {

    private final FindAllPeopleStarUseCase findAllPeopleStarUseCase;

    @Override
    @GetMapping(value = "/people")
    public ResponseEntity<List<PeopleStarResponse>> getAll() {
        var responseUsece = findAllPeopleStarUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(responseUsece);
    }

}
