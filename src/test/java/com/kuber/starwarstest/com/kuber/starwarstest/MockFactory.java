package com.kuber.starwarstest.com.kuber.starwarstest;

import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.gateway.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.gateway.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.gateway.response.SpecieStarGatewayResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MockFactory {

    public static PaginableResponse<PeopleStarGatewayResponse> getCompletePeopleGatewayApiResponde() {
        var response = getPeopleGatewayApiResponde();
        response.setCount(1);

        response.getResults().get(0).setHomeworld("1");
        response.getResults().get(0).setSpecies(Collections.singletonList("1"));

        return response;
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
