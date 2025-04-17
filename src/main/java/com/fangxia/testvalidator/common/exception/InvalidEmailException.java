package com.fangxia.testvalidator.common.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
    public InvalidEmailException(String message, Exception cause) {
        super(message, cause);
    }
}
