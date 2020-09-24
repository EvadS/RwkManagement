package com.se.management.exception;

public class UnreadableCredentialsException extends RuntimeException {

    public UnreadableCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
