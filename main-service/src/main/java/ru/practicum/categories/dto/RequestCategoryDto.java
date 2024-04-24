package ru.practicum.categories.dto;

import lombok.Data;
import ru.practicum.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RequestCategoryDto {
    @NotBlank
    @Size(min = 1, max = 255, groups = Create.class)
    private String name;
}
