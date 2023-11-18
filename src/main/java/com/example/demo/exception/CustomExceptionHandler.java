package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(AllException.class) //chỉ rõ method này xử lý exception nào
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handlerAllException(AllException ex){
        return new Response(HttpStatus.BAD_REQUEST,ex.getMessage());
    }
}
