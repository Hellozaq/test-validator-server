package com.fangxia.testvalidator.common.advice;

import com.fangxia.testvalidator.common.exception.InvalidEmailException;
import com.fangxia.testvalidator.common.model.ApiResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.ReflectionException;

@RestControllerAdvice
public class FXExceptionHandlerAdvice {

    @ExceptionHandler(InvalidEmailException.class)
    public ApiResponse<?> handleInvalidEmailException(InvalidEmailException iee) {
        return ApiResponse.failure(iee.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleAnyException(ReflectionException ex) {
        return ApiResponse.failure(ex.getMessage());
    }

}
