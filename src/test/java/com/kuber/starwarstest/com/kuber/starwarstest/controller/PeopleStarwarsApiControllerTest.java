package com.kuber.starwarstest.com.kuber.starwarstest.controller;

import com.kuber.starwarstest.com.kuber.starwarstest.MockFactory;
import com.kuber.starwarstest.controller.PeopleStarwarsApiController;
import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.gateway.api.PeopleStarwarsApiGateway;
import com.kuber.starwarstest.gateway.api.PlanetStarwarsApiGateway;
import com.kuber.starwarstest.gateway.api.SpecieStarwarsApiGateway;
import com.kuber.starwarstest.gateway.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.gateway.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.gateway.response.SpecieStarGatewayResponse;
import com.kuber.starwarstest.usecase.converter.PeopleStarwarGatewayToPeopleResponseConverter;
import com.kuber.starwarstest.usecase.converter.PlanetStarwarGatewayToPlanetResponseConverter;
import com.kuber.starwarstest.usecase.converter.SpecieStarwarGatewayToSpecieResponseConverter;
import com.kuber.starwarstest.usecase.impl.FindAllPeopleStarUseCaseImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PeopleStarwarsApiController.class)
@AutoConfigureMockMvc
@DisplayName("Starwars API People Controller Test")
public class PeopleStarwarsApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @SpyBean
    FindAllPeopleStarUseCaseImpl findAllPeopleStarUseCase;
    @MockBean
    PeopleStarwarsApiGateway peopleStarwarsApiGateway;
    @MockBean
    PlanetStarwarsApiGateway planetStarwarsApiGateway;
    @MockBean
    SpecieStarwarsApiGateway specieStarwarsApiGateway;
    @SpyBean
    PeopleStarwarGatewayToPeopleResponseConverter peopleStarwarGatewayToPeopleResponseConverter;
    @SpyBean
    SpecieStarwarGatewayToSpecieResponseConverter specieStarwarGatewayToSpecieResponseConverter;
    @SpyBean
    PlanetStarwarGatewayToPlanetResponseConverter planetStarwarGatewayToPlanetResponseConverter;

    static final String GET_ALL_PEOPLE = "/swapi/v1/people";

    @Test
    @DisplayName("Get All People By Page And Return Success")
    void getAllPeopleByPageAndReturnSuccess() throws Exception {
        //GIVEN
        final Integer pageTwo = 2;
        final PaginableResponse<PeopleStarGatewayResponse> responsePeopleGateway = MockFactory.getCompletePeopleGatewayApiResponde();
        final PlanetStarGatewayResponse responsePlanetGateway = MockFactory.getPlanetGatewayApiResponde();
        final SpecieStarGatewayResponse responseSpecieGateway = MockFactory.getSpecieGatewayApiResponde();

        //WHEN
        when(peopleStarwarsApiGateway.getAllPeoplePerPage(pageTwo))
                .thenReturn(responsePeopleGateway);
        when(planetStarwarsApiGateway.getPlanetById(1))
                .thenReturn(responsePlanetGateway);
        when(specieStarwarsApiGateway.getSpecieById(1))
                .thenReturn(responseSpecieGateway);

        ResultActions resultActions = mockMvc.perform(
                get(new URI(GET_ALL_PEOPLE + "?page=2"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        var responseUseCaseList = findAllPeopleStarUseCase.execute(pageTwo);
        var responseUseCase = responseUseCaseList.get(0);

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(responseUseCase.getName()))
                .andExpect(jsonPath("$[0].mass").value(responseUseCase.getMass()))
                .andExpect(jsonPath("$[0].height").value(responseUseCase.getHeight()))
                .andExpect(jsonPath("$[0].hair_color").value(responseUseCase.getHairColor()))
                .andExpect(jsonPath("$[0].eye_color").value(responseUseCase.getEyeColor()))
                .andExpect(jsonPath("$[0].skin_color").value(responseUseCase.getSkinColor()))
                .andExpect(jsonPath("$[0].gender").value(responseUseCase.getGender()))
                .andExpect(jsonPath("$[0].birth_year").value(responseUseCase.getBirthYear()))
                .andExpect(jsonPath("$[0].specie.id").value(responseUseCase.getSpecie().getId()))
                .andExpect(jsonPath("$[0].specie.name").value(responseUseCase.getSpecie().getName()))
                .andExpect(jsonPath("$[0].specie.classification").value(responseUseCase.getSpecie().getClassification()))
                .andExpect(jsonPath("$[0].specie.designation").value(responseUseCase.getSpecie().getDesignation()))
                .andExpect(jsonPath("$[0].specie.average_height").value(responseUseCase.getSpecie().getAverageHeight()))
                .andExpect(jsonPath("$[0].specie.average_lifespan").value(responseUseCase.getSpecie().getAverageLifespan()))
                .andExpect(jsonPath("$[0].specie.language").value(responseUseCase.getSpecie().getLanguage()))
                .andExpect(jsonPath("$[0].homeworld.id").value(responseUseCase.getHomeworld().getId()))
                .andExpect(jsonPath("$[0].homeworld.name").value(responseUseCase.getHomeworld().getName()))
                .andExpect(jsonPath("$[0].homeworld.climate").value(responseUseCase.getHomeworld().getClimate()))
                .andExpect(jsonPath("$[0].homeworld.gravity").value(responseUseCase.getHomeworld().getGravity()))
                .andExpect(jsonPath("$[0].homeworld.terrain").value(responseUseCase.getHomeworld().getTerrain()))
                .andExpect(jsonPath("$[0].homeworld.population").value(responseUseCase.getHomeworld().getPopulation()))
        ;
    }

    @Test
    @DisplayName("Get All People By Page And Return No Content")
    void getAllPeopleByPageAndReturnNoContent() throws Exception {
        //GIVEN
        final Integer unknowPage = 999999;

        //WHEN
        when(peopleStarwarsApiGateway.getAllPeoplePerPage(unknowPage))
                .thenReturn(new PaginableResponse<>());

        ResultActions resultActions = mockMvc.perform(
                get(new URI(GET_ALL_PEOPLE + "?page=999999"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        findAllPeopleStarUseCase.execute(unknowPage);

        resultActions.andDo(print())
                .andExpect(status().isNoContent())
        ;
    }

}