package ru.practicum.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ConflictException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}