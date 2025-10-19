package com.srdc.hw2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Handles EntityNotFoundException by returning a 404 Not Found response with an error message.
     *
     * @param ex The EntityNotFoundException thrown.
     * @return ResponseEntity containing an ErrorResponse with a 404 status and error message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles BadRequestException by returning a 400 Bad Request response with an error message.
     *
     * @param ex The BadRequestException thrown.
     * @return ResponseEntity containing an ErrorResponse with a 400 status and error message.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    /**
     * Handles ForbiddenRequestException by returning a 403 Forbidden response with an error message.
     *
     * @param ex The ForbiddenRequestException thrown.
     * @return ResponseEntity containing an ErrorResponse with a 403 status and error message.
     */
    @ExceptionHandler(ForbiddenRequestException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenRequestException(ForbiddenRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }


    /**
     * Handles UnauthorizedRequestException by returning a 401 Unauthorized response with an error message.
     *
     * @param ex The UnauthorizedRequestException thrown.
     * @return ResponseEntity containing an ErrorResponse with a 401 status and error message.
     */
    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedRequestException(UnauthorizedRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

}
