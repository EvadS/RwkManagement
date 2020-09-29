package com.se.management.exception.model;



public class ApiValidationError extends ApiSubError  {

    private String object;
    private String field;
    private String message;

    ApiValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ApiValidationError(String object, String field, String message) {
        this.object = object;
        this.field = field;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiValidationError() {
    }

    @Override
    public String toString() {
        return "ApiValidationError{" +
                "object='" + object + '\'' +
                ", field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}