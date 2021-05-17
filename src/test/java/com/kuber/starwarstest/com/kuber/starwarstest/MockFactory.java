package com.kuber.starwarstest.com.kuber.starwarstest;

import com.kuber.starwarstest.entrypoint.http.response.PaginableResponse;
import com.kuber.starwarstest.dataprovider.api.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.dataprovider.api.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.dataprovider.api.response.SpecieStarGatewayResponse;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.PlanetStarResponse;
import com.kuber.starwarstest.entrypoint.http.response.SpecieStarResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class MockFactory {

    private MockFactory() {}

    public static final String STARWARS_PEOPLE_API_RESPONSE = "{\"count\":1,\"next\":\"http://swapi.dev/api/people/?page=3\",\"previous\":\"http://swapi.dev/api/people/?page=1\",\"results\":[{\"name\":\"Anakin Skywalker\",\"height\":\"188\",\"mass\":\"84\",\"hair_color\":\"blond\",\"skin_color\":\"fair\",\"eye_color\":\"blue\",\"birth_year\":\"41.9BBY\",\"gender\":\"male\",\"homeworld\":\"http://swapi.dev/api/planets/1/\",\"films\":[\"http://swapi.dev/api/films/4/\",\"http://swapi.dev/api/films/5/\",\"http://swapi.dev/api/films/6/\"],\"species\":[],\"vehicles\":[\"http://swapi.dev/api/vehicles/44/\",\"http://swapi.dev/api/vehicles/46/\"],\"starships\":[\"http://swapi.dev/api/starships/39/\",\"http://swapi.dev/api/starships/59/\",\"http://swapi.dev/api/starships/65/\"],\"created\":\"2014-12-10T16:20:44.310000Z\",\"edited\":\"2014-12-20T21:17:50.327000Z\",\"url\":\"http://swapi.dev/api/people/11/\"}]}";
    public static final String STARWARS_PLANET_API_RESPONSE = "{\"name\":\"Yavin IV\",\"rotation_period\":\"24\",\"orbital_period\":\"4818\",\"diameter\":\"10200\",\"climate\":\"temperate, tropical\",\"gravity\":\"1 standard\",\"terrain\":\"jungle, rainforests\",\"surface_water\":\"8\",\"population\":\"1000\",\"residents\":[],\"films\":[\"http://swapi.dev/api/films/1/\"],\"created\":\"2014-12-10T11:37:19.144000Z\",\"edited\":\"2014-12-20T20:58:18.421000Z\",\"url\":\"http://swapi.dev/api/planets/3/\"}";
    public static final String STARWARS_SPECIES_API_RESPONSE = "{\"name\":\"Wookie\",\"classification\":\"mammal\",\"designation\":\"sentient\",\"average_height\":\"210\",\"skin_colors\":\"gray\",\"hair_colors\":\"black, brown\",\"eye_colors\":\"blue, green, yellow, brown, golden, red\",\"average_lifespan\":\"400\",\"homeworld\":\"http://swapi.dev/api/planets/14/\",\"language\":\"Shyriiwook\",\"people\":[\"http://swapi.dev/api/people/13/\",\"http://swapi.dev/api/people/80/\"],\"films\":[\"http://swapi.dev/api/films/1/\",\"http://swapi.dev/api/films/2/\",\"http://swapi.dev/api/films/3/\",\"http://swapi.dev/api/films/6/\"],\"created\":\"2014-12-10T16:44:31.486000Z\",\"edited\":\"2014-12-20T21:36:42.142000Z\",\"url\":\"http://swapi.dev/api/species/3/\"}";

    public static PaginableResponse<PeopleStarGatewayResponse> getCompletePeopleGatewayApiResponse() {
        var response = getPeopleGatewayApiResponde();
        response.setCount(1);

        response.getResults().get(0).setHomeworld("1");
        response.getResults().get(0).setSpecies(Collections.singletonList("1"));

        return response;
    }

    public static List<PeopleStarResponse> getCompletePeopleGatewayApiResponseList() {
        List<PeopleStarResponse> peopleListResponse = new ArrayList<>();

        peopleListResponse.add(
                PeopleStarResponse.builder()
                        .birthYear("19BBY")
                        .eyeColor("blue")
                        .gender("male")
                        .hairColor("blond")
                        .height("172")
                        .mass("77")
                        .name("Luke Skywalker")
                        .skinColor("fair")
                        .homeworld(
                                PlanetStarResponse.builder()
                                        .climate("arid")
                                        .name("Tatooine")
                                        .id(1)
                                        .gravity("1 standard")
                                        .terrain("desert")
                                        .population("200000")
                                .build()
                        )
                        .specie(
                                SpecieStarResponse.builder()
                                        .id(1)
                                        .name("Human")
                                        .classification("mammal")
                                        .designation("sentient")
                                        .averageHeight("180")
                                        .averageLifespan("120")
                                        .language("Galactic Basic")
                                        .build()
                        )
                        .build()
        );

        return peopleListResponse;
    }

    public static PaginableResponse<PeopleStarGatewayResponse> getPeopleGatewayApiResponde() {
        PaginableResponse<PeopleStarGatewayResponse> response = new PaginableResponse<>();
        response.setCount(1);

        List<PeopleStarGatewayResponse> peopleListResponse = new ArrayList<>();
        PeopleStarGatewayResponse peopleStarGatewayResponse = new PeopleStarGatewayResponse();
        peopleStarGatewayResponse.setGender("male");
        peopleStarGatewayResponse.setBirthYear("19BBY");
        peopleStarGatewayResponse.setSkinColor("fair");
        peopleStarGatewayResponse.setHairColor("blond");
        peopleStarGatewayResponse.setEyeColor("blue");
        peopleStarGatewayResponse.setHeight("172");
        peopleStarGatewayResponse.setMass("77");
        peopleStarGatewayResponse.setName("Luke Skywalker");
        peopleListResponse.add(peopleStarGatewayResponse);

        response.setResults(peopleListResponse);
        return response;
    }

    public static SpecieStarGatewayResponse getSpecieGatewayApiResponde() {
        var response = new SpecieStarGatewayResponse();

        response.setClassification("mammal");
        response.setAverageHeight("180");
        response.setAverageLifespan("120");
        response.setDesignation("sentient");
        response.setEyeColors("brown, blue, green, hazel, grey, amber");
        response.setLanguage("Galactic Basic");
        response.setHairColors("blonde, brown, black, red");
        response.setName("Human");
        response.setSkinColors("caucasian, black, asian, hispanic");

        return response;
    }

    public static PlanetStarGatewayResponse getPlanetGatewayApiResponde() {
        var response = new PlanetStarGatewayResponse();

        response.setName("Tatooine");
        response.setRotationPeriod("23");
        response.setOrbitalPeriod("orbital_period");
        response.setDiameter("10465");
        response.setClimate("arid");
        response.setGravity("1 standard");
        response.setTerrain("desert");
        response.setSurfaceWater("1");
        response.setPopulation("200000");

        return response;
    }

}
