package com.kuber.starwarstest.com.kuber.starwarstest.usecase;

import com.kuber.starwarstest.com.kuber.starwarstest.MockFactory;
import com.kuber.starwarstest.controller.response.PeopleStarResponse;
import com.kuber.starwarstest.gateway.api.PeopleStarwarsApiGateway;
import com.kuber.starwarstest.gateway.api.PlanetStarwarsApiGateway;
import com.kuber.starwarstest.gateway.api.SpecieStarwarsApiGateway;
import com.kuber.starwarstest.usecase.converter.PeopleStarwarGatewayToPeopleResponseConverter;
import com.kuber.starwarstest.usecase.converter.PlanetStarwarGatewayToPlanetResponseConverter;
import com.kuber.starwarstest.usecase.converter.SpecieStarwarGatewayToSpecieResponseConverter;
import com.kuber.starwarstest.usecase.impl.FindAllPeopleStarUseCaseImpl;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Usecase FindAllPeople Starwars API")
public class FindAllPeopleStarUseCaseTest {

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

    @Test
    @DisplayName("Get All Paginable People Of StarwarsApi with success")
    public void getAllPaginablePeopleOfStarwarsApiWithSuccess() {
        Integer pageOne = 1;
        when(peopleStarwarsApiGateway.getAllPeoplePerPage(pageOne))
                .thenReturn(MockFactory.getCompletePeopleGatewayApiResponde());

        when(specieStarwarsApiGateway.getSpecieById(1))
                .thenReturn(MockFactory.getSpecieGatewayApiResponde());

        when(planetStarwarsApiGateway.getPlanetById(1))
                .thenReturn(MockFactory.getPlanetGatewayApiResponde());

        List<PeopleStarResponse> responseUseCase = findAllPeopleStarUseCase.execute(pageOne);

        assertAll("People",
            () -> assertFalse(responseUseCase.isEmpty()),
            () -> {
                PeopleStarResponse expectedResponse = responseUseCase.get(0);

                assertAll("Own characteristics, species and your planet",
                    () -> assertEquals("Luke Skywalker", expectedResponse.getName()),
                    () -> assertEquals("male", expectedResponse.getGender()),
                    () -> assertEquals("19BBY", expectedResponse.getBirthYear()),
                    () -> assertEquals("fair", expectedResponse.getSkinColor()),
                    () -> assertEquals("blond", expectedResponse.getHairColor()),
                    () -> assertEquals("blue", expectedResponse.getEyeColor()),
                    () -> assertEquals("172", expectedResponse.getHeight()),
                    () -> assertEquals("77", expectedResponse.getMass()),

                    () -> assertEquals(1, expectedResponse.getSpecie().getId()),
                    () -> assertEquals("mammal", expectedResponse.getSpecie().getClassification()),
                    () -> assertEquals("sentient", expectedResponse.getSpecie().getDesignation()),
                    () -> assertEquals("180", expectedResponse.getSpecie().getAverageHeight()),
                    () -> assertEquals("120", expectedResponse.getSpecie().getAverageLifespan()),
                    () -> assertEquals("Galactic Basic", expectedResponse.getSpecie().getLanguage()),
                    () -> assertEquals("Human", expectedResponse.getSpecie().getName()),

                    () -> assertEquals(1, expectedResponse.getHomeworld().getId()),
                    () -> assertEquals("Tatooine", expectedResponse.getHomeworld().getName()),
                    () -> assertEquals("arid", expectedResponse.getHomeworld().getClimate()),
                    () -> assertEquals("1 standard", expectedResponse.getHomeworld().getGravity()),
                    () -> assertEquals("desert", expectedResponse.getHomeworld().getTerrain()),
                    () -> assertEquals("200000", expectedResponse.getHomeworld().getPopulation())
                );
            }
        );

    }

    @Test
    @DisplayName("Get All Paginable People Of StarwarsApi Without Specie and Planet and Return success")
    public void getAllPaginablePeopleOfStarwarsApiWithoutSpecieAndPlaneceReturnSuccess() {
        Integer pageOne = 1;
        when(peopleStarwarsApiGateway.getAllPeoplePerPage(pageOne))
                .thenReturn(MockFactory.getPeopleGatewayApiResponde());

        List<PeopleStarResponse> responseUseCase = findAllPeopleStarUseCase.execute(pageOne);

        assertFalse(responseUseCase.isEmpty());

        PeopleStarResponse expectedResponse = responseUseCase.get(0);
        assertEquals("Luke Skywalker", expectedResponse.getName());
        assertEquals("male", expectedResponse.getGender());
        assertEquals("19BBY", expectedResponse.getBirthYear());
        assertEquals("fair", expectedResponse.getSkinColor());
        assertEquals("blond", expectedResponse.getHairColor());
        assertEquals("blue", expectedResponse.getEyeColor());
        assertEquals("172", expectedResponse.getHeight());
        assertEquals("77", expectedResponse.getMass());
    }

    @Test
    @DisplayName("Get All Paginable People Of StarwarsApi And Return Not Found")
    public void getAllPaginablePeopleOfStarwarsApiReturnNotFound() {
        Integer unknowPage = 99999;
        when(peopleStarwarsApiGateway.getAllPeoplePerPage(unknowPage))
                .thenThrow(FeignException.NotFound.class);

        FeignException.NotFound notFoundException = assertThrows(
                FeignException.NotFound.class,
                () -> findAllPeopleStarUseCase.execute(unknowPage)
        );

        assertNotNull(notFoundException);
    }

}
