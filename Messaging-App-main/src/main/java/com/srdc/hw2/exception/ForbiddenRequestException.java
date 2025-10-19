package com.srdc.hw2.exception;

public class ForbiddenRequestException extends RuntimeException{
    public ForbiddenRequestException() {}

    public ForbiddenRequestException(String message) {
        super(message);
    }
}
