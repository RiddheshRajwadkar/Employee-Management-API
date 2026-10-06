package com.EmployeeManagement.demo.exception;

public class ResourceNotFoundException extends RuntimeException {

    public static final Long serializeUID = 1L;
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
