package com.kuber.starwarstest.com.kuber.starwarstest.entrypoint.http;

import com.kuber.starwarstest.com.kuber.starwarstest.MockFactory;
import com.kuber.starwarstest.core.usecase.FindAllPeopleStarUseCase;
import com.kuber.starwarstest.core.usecase.FindPeopleByIdUseCase;
import com.kuber.starwarstest.dataprovider.api.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.entrypoint.http.PeopleStarwarsApiController;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PeopleStarwarsApiController.class)
@AutoConfigureMockMvc
@DisplayName("Starwars API People Controller Test")
public class PeopleStarwarsApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FindAllPeopleStarUseCase findAllPeopleStarUseCase;

    @MockBean(name = "FindPeopleByIdExternalApiUseCaseImpl")
    FindPeopleByIdUseCase findPeopleByIdExternalApiUseCase;

    @MockBean(name = "FindPeopleByIdDbUseCaseImpl")
    FindPeopleByIdUseCase findPeopleByIdDbUseCase;

    static final String GET_ALL_PEOPLE = "/swapi/v1/people";

    @Test
    @DisplayName("Get All People By Page And Return Success")
    void getAllPeopleByPageAndReturnSuccess() throws Exception {
        //GIVEN
        final Integer pageTwo = 2;
        final List<PeopleStarResponse> responsePeopleGateway = MockFactory.getCompletePeopleGatewayApiResponseList();

        //WHEN
        when(findAllPeopleStarUseCase.execute(pageTwo))
                .thenReturn(responsePeopleGateway);

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
        when(findAllPeopleStarUseCase.execute(unknowPage))
                .thenReturn(Collections.emptyList());

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
