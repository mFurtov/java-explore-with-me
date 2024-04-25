package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
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
    @ExceptionHandler({MethodArgumentTypeMismatchException.class,ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validCountException(final RuntimeException e) {
        log.debug("Ошибка валидации 400 Bad request {}", e.getMessage());
        log.debug("Ошибка валидации 400 Bad request {}", e.getMessage());
        if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) e;
            return new ErrorResponse(
                    "BAD_REQUEST",
                    "Incorrectly made request.",
                    "Failed to convert value of type '" + ex.getValue().getClass().getSimpleName() + "' to required type '" + ex.getRequiredType().getSimpleName() + "'; nested exception is " + ex.getCause(),
                    LocalDateTime.now().toString()
            );
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            StringBuilder message = new StringBuilder();
            ex.getConstraintViolations().forEach(violation -> {
                message.append("Field: ").append(violation.getPropertyPath()).append(". Error: ").append(violation.getMessage()).append(". Value: ").append(violation.getInvalidValue()).append("; ");
            });
            return new ErrorResponse(
                    "BAD_REQUEST",
                    "Incorrectly made request.",
                    message.toString(),
                    LocalDateTime.now().toString()
            );
        }
        return new ErrorResponse(
                "BAD_REQUEST",
                "Validation failed.",
                e.getMessage(),
                LocalDateTime.now().toString()
        );
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error("Объект не найден: {}", e.getMessage());
        return new ErrorResponse(
                "NOT_FOUND",
                "The required object was not found.",
                e.getMessage(),
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
