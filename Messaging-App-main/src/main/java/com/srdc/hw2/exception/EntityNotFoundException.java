package com.srdc.hw2.exception;


public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {}

    public EntityNotFoundException(String message) {
        super(message);
    }
}
