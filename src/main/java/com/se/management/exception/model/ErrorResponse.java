package com.se.management.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Base model to customize the entire JSON error response :
 */
public class ErrorResponse {
    private HttpStatus status;

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<ApiSubError> subErrors;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;


    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus status) {
        this();
        this.status = status;
    }

    public ErrorResponse(String message) {
        this();
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

    /**
     * Base exception info
     *
     * @param httpStatus  http status code
     * @param message user - friendly message about validation status
     * @param subErrors information about each error
     */
    public ErrorResponse(HttpStatus httpStatus, String message, List<ApiSubError> subErrors) {
       this();
        this.status = httpStatus;
        this.message = message;
        this.subErrors = subErrors;
    }

    public ErrorResponse(HttpStatus httpStatus, String message, String subErrors) {
        this();
        this.status = httpStatus;
        this.message = message;

        ApiSubError apiSubError = new  ApiValidationError("", subErrors);
        this.subErrors =  Arrays.asList(apiSubError);
    }

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this();
        this.status = httpStatus;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public void addSubErrors(ApiSubError apiSubError) {
        if (subErrors == null) {
            subErrors = new ArrayList();
        }

        subErrors.add(apiSubError);
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
