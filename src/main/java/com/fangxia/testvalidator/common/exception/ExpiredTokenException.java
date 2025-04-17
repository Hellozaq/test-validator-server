package com.fangxia.testvalidator.common.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String message) {
        super(message);
    }
    public ExpiredTokenException(String message, Exception cause) {
        super(message, cause);
    }
}
