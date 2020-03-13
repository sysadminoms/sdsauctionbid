package com.oms.sdsauctionbid.controller;

import com.oms.sdsauctionbid.domain.CustomMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

@RestControllerAdvice

public class RestExceptionHandler {

    @ExceptionHandler(value = {ServletException.class})
    public ResponseEntity servletException(ServletException e) {
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (message.equals("token_expired")) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            message = "the token is expired and not valid anymore";
        }
        return new ResponseEntity<>(new CustomMessageResponse("Token Expired"
                ,-1), HttpStatus.UNAUTHORIZED);
    }
}
