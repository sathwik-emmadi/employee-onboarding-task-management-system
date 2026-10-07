package com.eots.exception;

public class UnauthorizedTaskAccessException extends RuntimeException {

    public UnauthorizedTaskAccessException(String message) {
        super(message);
    }

}