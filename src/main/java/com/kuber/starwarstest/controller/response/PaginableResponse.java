package com.kuber.starwarstest.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginableResponse<T extends ResponseBase> {

    private int count;
    private List<T> results;

}
