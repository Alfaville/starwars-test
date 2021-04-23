package com.kuber.starwarstest.entrypoint.http.converter;

import com.kuber.starwarstest.core.entity.GenderEnum;
import com.kuber.starwarstest.core.entity.PersonEntity;
import com.kuber.starwarstest.entrypoint.http.response.PeopleStarResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class PeopleResponseToPeopleEntityConverter implements Converter<PeopleStarResponse, PersonEntity> {

    private static final String UNKNOWN_TYPE = "unknown";
    private static final String UNKNOWN_GENDER = "n/a";

    @Override
    public PersonEntity convert(PeopleStarResponse source) {
        return PersonEntity.builder()
                .birthYear(source.getBirthYear())
                .name(source.getName())
                .eyeColor(source.getEyeColor())
                .gender(parseEnumUnknown(source.getGender().toUpperCase()))
                .hairColor(source.getHairColor())
                .height(parseIntUnknown(source.getHeight()))
                .mass(parseDoubleUnknown(source.getMass()))
                .skinColor(source.getSkinColor())
//                            .specie(source.getSpecie()) TODO
//                            .planet()
                .build();
    }

    private Integer parseIntUnknown(String value) {
        return value.equalsIgnoreCase(UNKNOWN_TYPE) ? null : Integer.parseInt(value);
    }

    private Double parseDoubleUnknown(String value) {
        return value.equalsIgnoreCase(UNKNOWN_TYPE) ? null : Double.parseDouble(value.replace(",", "."));
    }

    private GenderEnum parseEnumUnknown(String value) {
        return value.equalsIgnoreCase(UNKNOWN_GENDER) ? GenderEnum.UNKNOWN : GenderEnum.valueOf(value);
    }

}