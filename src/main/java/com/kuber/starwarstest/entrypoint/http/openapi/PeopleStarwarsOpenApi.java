package com.kuber.starwarstest.entrypoint.http.openapi;

import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = PeopleStarwarsOpenApi.TAG, description = "API of the Starwars people and their respective planets and species")
public interface PeopleStarwarsOpenApi {

    String TAG = "People Warrior";

    @Operation(summary = "Get all starwars people", tags = {TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PeopleStarResponse.class)),
            }),
    })
    ResponseEntity<List<PeopleStarResponse>> getAll(
            @Parameter(required = true, description = "Field where you can choose some page") Integer page);

}
