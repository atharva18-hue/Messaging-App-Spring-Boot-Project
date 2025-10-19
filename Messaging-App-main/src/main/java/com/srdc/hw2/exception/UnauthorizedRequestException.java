package com.srdc.hw2.exception;

public class UnauthorizedRequestException extends RuntimeException{
    public UnauthorizedRequestException() {}
    public UnauthorizedRequestException(String message) {super(message);}
}
