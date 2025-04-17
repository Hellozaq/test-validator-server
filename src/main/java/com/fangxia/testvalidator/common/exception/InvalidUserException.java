package com.fangxia.testvalidator.common.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
    public InvalidUserException(String message, Exception cause) {
        super(message, cause);
    }
}
