package com.se.management.exception;

import lombok.Data;

@Data
public class Violation {

    private final String fieldName;

    private final String message;

    // ...
}
