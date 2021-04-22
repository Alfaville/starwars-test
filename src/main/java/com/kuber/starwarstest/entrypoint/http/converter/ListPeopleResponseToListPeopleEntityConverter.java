package com.kuber.starwarstest.entrypoint.http.converter;

import com.kuber.starwarstest.core.entity.GenderEnum;
import com.kuber.starwarstest.core.entity.PersonEntity;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListPeopleResponseToListPeopleEntityConverter implements Converter<List<PeopleStarResponse>, List<PersonEntity>> {

    private static final String UNKNOWN_TYPE = "unknown";

    @Override
    public List<PersonEntity> convert(List<PeopleStarResponse> source) {
        final List<PersonEntity> listPeopleEntity = new ArrayList<>();
        source.forEach(dto ->
            listPeopleEntity.add(
                    PersonEntity.builder()
                            .birthYear(dto.getBirthYear())
                            .name(dto.getName())
                            .eyeColor(dto.getEyeColor())
                            .gender(GenderEnum.valueOf(dto.getGender().toUpperCase()))
                            .hairColor(dto.getHairColor())
                            .height(parseIntUnknown(dto.getHeight()))
                            .mass(parseDoubleUnknown(dto.getMass()))
                            .skinColor(dto.getSkinColor())
//                            .specie(dto.getSpecie()) TODO
//                            .planet()
                            .build()
            )
        );
        return listPeopleEntity;
    }

    private Integer parseIntUnknown(String value) {
        return value.equalsIgnoreCase(UNKNOWN_TYPE) ? null : Integer.parseInt(value);
    }

    private Double parseDoubleUnknown(String value) {
        return value.equalsIgnoreCase(UNKNOWN_TYPE) ? null : Double.parseDouble(value.replace(",", "."));
    }

}