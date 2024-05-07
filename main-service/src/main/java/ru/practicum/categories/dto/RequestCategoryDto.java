package ru.practicum.categories.dto;

import lombok.Data;
import ru.practicum.Create;
import ru.practicum.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RequestCategoryDto {
    @NotBlank(groups = Create.class)
    @Size(min = 1, max = 50, groups = {Create.class, Update.class})
    private String name;
}
