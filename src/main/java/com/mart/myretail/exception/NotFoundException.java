package com.mart.myretail.exception;

/**
 * Exception thrown when resource not found
 */
public class NotFoundException extends Exception {
    String message;

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "NotFoundException {" +
                "message='" + message + '\'' +
                '}';
    }
}
