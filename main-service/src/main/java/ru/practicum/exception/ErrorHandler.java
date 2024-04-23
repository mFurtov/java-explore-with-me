package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validCountException(final MethodArgumentNotValidException e) {
        log.debug("Ошибка валидации 400 Bad request {}", e.getMessage());
        return new ErrorResponse(
                "BAD_REQUEST",
                "Incorrectly made request.",
                e.getFieldError().getField() + ". Error: " + e.getFieldError().getDefaultMessage() + ". Value: " + e.getFieldError().getRejectedValue(),
                LocalDateTime.now().toString()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("Ошибка нарушения целостности данных: {}", e.getMessage());
        return new ErrorResponse(
                "CONFLICT",
                "Integrity constraint has been violated.",
                e.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
