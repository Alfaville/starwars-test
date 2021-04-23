package com.kuber.starwarstest.core.usecase.converter;

import com.kuber.starwarstest.core.entity.SpecieEntity;
import com.kuber.starwarstest.dataprovider.api.response.SpecieStarGatewayResponse;
import com.kuber.starwarstest.entrypoint.http.response.SpecieStarResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Lazy
@Component
public class SpecieEntityToSpecieResponseConverter implements Converter<SpecieEntity, SpecieStarResponse> {

    @Override
    public SpecieStarResponse convert(SpecieEntity source) {
        if(isNull(source)) {
            return null;
        }
        return SpecieStarResponse.builder()
                .id(source.getId().intValue())
                .averageHeight(source.getAverageHeight())
                .averageLifespan(source.getAverageLifespan())
                .classification(source.getClassification())
                .name(source.getName())
                .designation(source.getDesignation())
                .language(source.getLanguage())
                .build();
    }

}