package com.fangxia.testvalidator.common.exception;

public class SQLFailureException extends RuntimeException {
    public SQLFailureException(String message) {
        super(message);
    }
    public SQLFailureException(String message, Exception cause) {
        super(message, cause);
    }
}
