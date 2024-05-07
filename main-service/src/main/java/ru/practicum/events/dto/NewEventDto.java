package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Create;
import ru.practicum.events.model.Event;
import ru.practicum.events.validator.DataValid;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
public class NewEventDto {
    @NotBlank(groups = Create.class)
    @Size(min = 20, max = 2000, groups = Create.class)
    private String annotation;
    @NotNull(groups = Create.class)
    private Integer category;
    @NotBlank(groups = Create.class)
    @Size(min = 20, max = 7000, groups = Create.class)
    private String description;
    @NotBlank(groups = Create.class)
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", groups = Create.class)
    @DataValid(groups = Create.class)
    private String eventDate;
    private Event.Location location;
    private Boolean paid = false;
    @PositiveOrZero(groups = Create.class)
    private int participantLimit = 0;
    private Boolean requestModeration = true;
    @NotBlank(groups = Create.class)
    @Size(min = 3, max = 120, groups = Create.class)
    private String title;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        @NotBlank(groups = Create.class)
        private double lat;
        @NotBlank(groups = Create.class)
        private double lon;
    }
}
