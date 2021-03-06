package com.kuber.starwarstest.config;

import com.kuber.starwarstest.entrypoint.http.response.ApiResponse;
import feign.FeignException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class ControllerExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> clienteException(Exception e, HttpServletRequest req) {
        final ApiResponse apiResponse = new ApiResponse(HttpStatus.PRECONDITION_FAILED, e.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(apiResponse);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ApiResponse> clienteNotFoundException(FeignException.NotFound e, HttpServletRequest req) {
        final ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst().orElse(ex.getMessage());

        ApiResponse err = new ApiResponse(status, errorMsg);
        return new ResponseEntity<>(err, status);
    }

}
