package com.kuber.starwarstest.usecase.converter;

import com.kuber.starwarstest.controller.response.SpecieStarResponse;
import com.kuber.starwarstest.gateway.response.SpecieStarGatewayResponse;
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