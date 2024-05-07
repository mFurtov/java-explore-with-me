package ru.practicum.compilations.dto;

import lombok.Data;
import ru.practicum.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class NewCompilationDto {
    private List<Integer> events;
    private Boolean pinned = false;
    @NotBlank(groups = Create.class)
    @Size(min = 1, max = 50, groups = Create.class)
    private String title;
}
