package com.kuber.starwarstest;

import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

//@ActiveProfiles("test")
//@ExtendWith(MockServerExtension.class)
//@SpringBootTest(
//		classes = StarwarsTestApplication.class,
//		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//)
class IntegrationTest {

//	@LocalServerPort
//	private int localServerPort;

//	@Rule
	public MockServerContainer mockServer = new MockServerContainer(DockerImageName.parse("jamesdbloom/mockserver:latest")); //mockserver/mockserver

//	@Test
	void contextLoads() {
		mockServer.start();

		new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
			.when(request()
        		.withPath("/people")
        		.withQueryStringParameter("page", "2")
			).respond(response()
				.withStatusCode(200)
        		.withBody("Peter the person!")
			);
	}

}
//https://www.jessym.com/articles/java-integration-testing