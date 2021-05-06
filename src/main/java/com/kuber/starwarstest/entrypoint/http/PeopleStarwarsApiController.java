package com.kuber.starwarstest.entrypoint.http;

import com.kuber.starwarstest.core.usecase.FindAllPeopleStarUseCase;
import com.kuber.starwarstest.core.usecase.FindPeopleByIdUseCase;
import com.kuber.starwarstest.entrypoint.http.openapi.PeopleStarwarsOpenApi;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping(path = "/swapi/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PeopleStarwarsApiController implements PeopleStarwarsOpenApi {

    private final FindAllPeopleStarUseCase findAllPeopleStarUseCase;
    private final FindPeopleByIdUseCase findPeopleByIdDbUseCase;
    private final FindPeopleByIdUseCase findPeopleByIdExternalApiUseCase;

    public PeopleStarwarsApiController(
            FindAllPeopleStarUseCase findAllPeopleStarUseCase,
            @Qualifier("FindPeopleByIdDbUseCaseImpl") FindPeopleByIdUseCase findPeopleByIdDbUseCase,
            @Qualifier("FindPeopleByIdExternalApiUseCaseImpl") FindPeopleByIdUseCase findPeopleByIdExternalApiUseCase
    ) {
        this.findAllPeopleStarUseCase = findAllPeopleStarUseCase;
        this.findPeopleByIdDbUseCase = findPeopleByIdDbUseCase;
        this.findPeopleByIdExternalApiUseCase = findPeopleByIdExternalApiUseCase;
    }

    @Override
    @GetMapping(value = "/people", params = "page")
    public ResponseEntity<List<PeopleStarResponse>> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        var responseUsecase = findAllPeopleStarUseCase.execute(page);

        if(responseUsecase.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(responseUsecase);
        }
    }

    @Override
    @GetMapping(value = "/people/{id}")
    public ResponseEntity<PeopleStarResponse> getById(@PathVariable("id") Long id) {
        var responseUsecase = findPeopleByIdDbUseCase.execute(id)
                .orElseGet(() -> findPeopleByIdExternalApiUseCase.execute(id)
                        .orElse(null));
        if(isNull(responseUsecase)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseUsecase);
    }

}
