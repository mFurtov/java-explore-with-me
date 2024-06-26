package ru.practicum.compilations.dto;

import lombok.Data;
import ru.practicum.Update;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<Integer> events;
    private Boolean pinned;
    @Size(min = 1, max = 50, groups = Update.class)
    private String title;
}
