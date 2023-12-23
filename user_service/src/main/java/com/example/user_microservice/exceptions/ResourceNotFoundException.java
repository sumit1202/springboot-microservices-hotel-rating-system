package com.example.user_microservice.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource Not Found.");
    }

    public ResourceNotFoundException(String message) {
        super("Resource Not Found: " + message);
    }
}
