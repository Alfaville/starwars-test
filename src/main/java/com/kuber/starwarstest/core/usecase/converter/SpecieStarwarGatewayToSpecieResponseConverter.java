package com.kuber.starwarstest.core.usecase.converter;

import com.kuber.starwarstest.entrypoint.http.response.SpecieStarResponse;
import com.kuber.starwarstest.dataprovider.api.response.SpecieStarGatewayResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpecieStarwarGatewayToSpecieResponseConverter implements Converter<SpecieStarGatewayResponse, SpecieStarResponse> {

    @Override
    public SpecieStarResponse convert(SpecieStarGatewayResponse source) {
        return SpecieStarResponse.builder()
                .averageHeight(source.getAverageHeight())
                .averageLifespan(source.getAverageLifespan())
                .classification(source.getClassification())
                .name(source.getName())
                .designation(source.getDesignation())
                .language(source.getLanguage())
                .build();
    }

}