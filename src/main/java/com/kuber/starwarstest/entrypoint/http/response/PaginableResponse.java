package com.kuber.starwarstest.entrypoint.http.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PaginableResponse<T extends ResponseBase> {

    private int count;
    private List<T> results;

    public List<T> getResults() {
        if(CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        return results;
    }

}
