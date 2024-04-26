package ru.practicum.events.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataValidator implements ConstraintValidator<DataValid, String> {
    @Override
    public void initialize(DataValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(value, formatter);
        if(start.isAfter(LocalDateTime.now().plusMinutes(119))){
            return true;
        }else {
            return false;
        }
    }
}
