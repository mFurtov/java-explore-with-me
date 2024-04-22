package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDtoRequest {
    @Size(min = 1, max = 255, groups = {Create.class})
    @NotBlank(groups = Create.class)
    private String app;
    @Size(min = 1, max = 255, groups = {Create.class})
    @NotBlank(groups = Create.class)
    private String uri;
    @Size(min = 1, max = 50, groups = {Create.class})
    @NotBlank(groups = Create.class)
    private String ip;
    @NotBlank(groups = Create.class)
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", groups = Create.class)
    private String timestamp;
}
