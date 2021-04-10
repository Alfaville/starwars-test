package com.kuber.starwarstest;

import com.kuber.starwarstest.com.kuber.starwarstest.MockFactory;
import com.kuber.starwarstest.controller.response.PaginableResponse;
import com.kuber.starwarstest.gateway.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.gateway.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.gateway.response.SpecieStarGatewayResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@DisplayName("Integrated test with external API - Star Wars")
@SpringJUnitConfig(TestConfiguration.class)
class IntegrationTestExternalApiStarwars {

	public static MockServerContainer mockServer = new MockServerContainer(DockerImageName.parse("jamesdbloom/mockserver:latest"));
	private static String BASE_PATH;
	private static MockServerClient mockServerClient;

	private final RestTemplate restTemplate;

	@Autowired
	public IntegrationTestExternalApiStarwars(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@BeforeAll
	public static void startMockServer() {
		mockServer.start();
		BASE_PATH = String.format("http://%s:%d", mockServer.getHost(), mockServer.getServerPort());
		mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort());

		System.setProperty("http.proxyHost", mockServer.getHost());
		System.setProperty("http.proxyPort", String.valueOf(mockServer.getServerPort()));
	}

	@AfterAll
	public static void stop() {
		mockServer.stop();
	}

	@Test
	@DisplayName("Get planet by id 3 and returns status HTTP code 200")
	public void get_planet_by_id_3_and_returns_HTTP_code_200() {
		//GIVEN
		final HttpRequest httpRequest = request().withMethod("GET").withPath("/api/planets/3/");

		final String logMessage = mockServerClient.retrieveLogMessages(httpRequest);

		mockServerClient
				.when(httpRequest)
				.respond(
						response()
								.withStatusCode(HttpStatus.OK.value())
								.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
								.withBody(MockFactory.STARWARS_PLANET_API_RESPONSE)
				);

		//WHEN
		var expectedResponse = getPlanetById(3L);

		//THEN
		assertAll("mock-server http - https://swapi.dev/api/planets/3/",
				() -> assertEquals(HttpStatus.OK, expectedResponse.getStatusCode()),
				() -> {
					var results = expectedResponse.getBody();
					assertAll(
							() -> assertEquals("Yavin IV", results.getName()),
							() -> assertEquals("24", results.getRotationPeriod()),
							() -> assertEquals("4818", results.getOrbitalPeriod()),
							() -> assertEquals("10200", results.getDiameter()),
							() -> assertEquals("temperate, tropical", results.getClimate()),
							() -> assertEquals("1 standard", results.getGravity()),
							() -> assertEquals("8", results.getSurfaceWater()),
							() -> assertEquals("1000", results.getPopulation())
					);
				}
		);

		mockServerClient.verify(
				request()
						.withMethod("GET")
						.withPath("/api/planets/3/")
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)),
				VerificationTimes.atLeast(1)
		);
	}

	@Test
	@DisplayName("Get all people per page 2 and returns status HTTP code 200")
	public void get_all_people_per_page_2_and_returns_HTTP_code_200() {
		//GIVEN
		mockServerClient
				.when(
						request()
								.withMethod("GET")
								.withPath("/api/people/")
								.withQueryStringParameter("page", "2")
				)
				.respond(
						response()
								.withStatusCode(HttpStatus.OK.value())
								.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
								.withBody(MockFactory.STARWARS_PEOPLE_API_RESPONSE)
				);

		//WHEN
		var expectedResponse = getAllPeopleByPage2();

		//THEN
		assertAll("mock-server http - http://swapi.dev/api/people/?page=2",
				() -> assertEquals(HttpStatus.OK, expectedResponse.getStatusCode()),
				() -> assertEquals(1, expectedResponse.getBody().getCount()),
				() -> {
					var results = expectedResponse.getBody().getResults();
					assertAll(
							() -> assertEquals("Anakin Skywalker", results.get(0).getName()),
							() -> assertEquals("41.9BBY", results.get(0).getBirthYear()),
							() -> assertEquals("fair", results.get(0).getSkinColor()),
							() -> assertEquals("blond", results.get(0).getHairColor()),
							() -> assertEquals("blue", results.get(0).getEyeColor()),
							() -> assertEquals("188", results.get(0).getHeight()),
							() -> assertEquals("84", results.get(0).getMass())
					);
				}
		);

		mockServerClient.verify(
				request()
						.withMethod("GET")
						.withPath("/api/people/")
						.withQueryStringParameter("page", "2")
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)),
				VerificationTimes.atLeast(1)
		);
	}

	@Test
	@DisplayName("Get species by id 3 and returns status HTTP code 200")
	public void get_species_by_id_3_and_returns_HTTP_code_200() {
		//GIVEN
		mockServerClient
				.when(
						request()
								.withMethod("GET")
								.withPath("/api/species/3/")
				)
				.respond(
						response()
								.withStatusCode(HttpStatus.OK.value())
								.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
								.withBody(MockFactory.STARWARS_SPECIES_API_RESPONSE)
				);

		//WHEN
		ResponseEntity<SpecieStarGatewayResponse> expectedResponse = getSpeciesById(3L);

		//THEN
		assertAll("mock-server http - http://swapi.dev/api/people/?page=2",
				() -> assertEquals(HttpStatus.OK, expectedResponse.getStatusCode()),
				() -> {
					var results = expectedResponse.getBody();
					assertAll(
							() -> assertEquals("Wookie", results.getName()),
							() -> assertEquals("mammal", results.getClassification()),
							() -> assertEquals("sentient", results.getDesignation()),
							() -> assertEquals("210", results.getAverageHeight()),
							() -> assertEquals("gray", results.getSkinColors()),
							() -> assertEquals("black, brown", results.getHairColors()),
							() -> assertEquals("blue, green, yellow, brown, golden, red", results.getEyeColors()),
							() -> assertEquals("400", results.getAverageLifespan()),
							() -> assertEquals("Shyriiwook", results.getLanguage())
					);
				}
		);

		mockServerClient.verify(
				request()
						.withMethod("GET")
						.withPath("/api/species/3/")
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)),
				VerificationTimes.atLeast(1)
		);
	}

	private ResponseEntity<PaginableResponse<PeopleStarGatewayResponse>> getAllPeopleByPage2() {
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<PaginableResponse<PeopleStarGatewayResponse>> expectedResponse = restTemplate.exchange(BASE_PATH + "/api/people/?page=2",
				HttpMethod.GET,
				new HttpEntity<>(headers),
				new ParameterizedTypeReference<>() {}
		);
		return expectedResponse;
	}

	private ResponseEntity<PlanetStarGatewayResponse> getPlanetById(Long id) {
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<PlanetStarGatewayResponse> expectedResponse = restTemplate.exchange(BASE_PATH + String.format("/api/planets/%d/", id),
				HttpMethod.GET,
				new HttpEntity<>(headers),
				new ParameterizedTypeReference<>() {}
		);
		return expectedResponse;
	}

	private ResponseEntity<SpecieStarGatewayResponse> getSpeciesById(Long id) {
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<SpecieStarGatewayResponse> expectedResponse = restTemplate.exchange(BASE_PATH + String.format("/api/species/%d/", id),
				HttpMethod.GET,
				new HttpEntity<>(headers),
				new ParameterizedTypeReference<>() {}
		);
		return expectedResponse;
	}

}
