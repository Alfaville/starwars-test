package com.kuber.starwarstest.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ApiResponse implements ResponseBase {
    private final HttpStatus status;
    private final String message;
}
