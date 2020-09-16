package com.se.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
