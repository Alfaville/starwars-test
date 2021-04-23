package com.kuber.starwarstest.dataprovider.fallback;

import com.kuber.starwarstest.dataprovider.api.StarwarsApiGateway;
import com.kuber.starwarstest.dataprovider.api.response.PeopleStarGatewayResponse;
import com.kuber.starwarstest.dataprovider.api.response.PlanetStarGatewayResponse;
import com.kuber.starwarstest.dataprovider.api.response.SpecieStarGatewayResponse;
import com.kuber.starwarstest.entrypoint.http.response.PaginableResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class StarwarsApiGatewayFallback implements FallbackFactory<StarwarsApiGateway> {

    @Override
    public StarwarsApiGateway create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            log.error(msg);
        }
        return new StarwarsApiGateway() {

            @Override
            public PaginableResponse<PeopleStarGatewayResponse> getAllPeoplePerPage(Integer page) {
                return new PaginableResponse<>();
            }

            @Override
            public PeopleStarGatewayResponse getPeopleById(Integer id) {
                return new PeopleStarGatewayResponse();
            }

            @Override
            public PlanetStarGatewayResponse getPlanetById(Integer id) {
                return new PlanetStarGatewayResponse();
            }

            @Override
            public SpecieStarGatewayResponse getSpecieById(Integer id) {
                return new SpecieStarGatewayResponse();
            }
        };
    }
}
