package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validCountException(final RuntimeException e) {
        log.debug("Ошибка валидации 400 Bad request {}", e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Incorrectly made request", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationExceptions(MethodArgumentNotValidException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Incorrectly made request.", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(final NotFoundException e) {
        log.debug("Объект не найден", e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND, "The required object was not found.", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictException(final ConflictException e) {
        log.debug("Конфликт", e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT, "Integrity constraint has been violated.", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse conflictException(final ForbiddenException e) {
        log.debug("Дата неверна", e.getMessage());
        return new ErrorResponse(HttpStatus.FORBIDDEN, "For the requested operation the conditions are not met.", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("Ошибка нарушения целостности данных: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT, "Integrity constraint has been violated.", e.getMessage(), LocalDateTime.now());
    }

}