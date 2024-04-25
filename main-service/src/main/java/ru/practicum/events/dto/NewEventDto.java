package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Create;
import ru.practicum.categories.model.Category;
import ru.practicum.events.model.Event;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Data
public class NewEventDto {
    @NotBlank(groups = Create.class)
    @Size(min = 20, max = 200, groups = Create.class)
    private String annotation;
    @NotBlank(groups = Create.class)
    @Size(min = 1, max = 999, groups = Create.class)
    private int category;
    @NotBlank(groups = Create.class)
    @Size(min = 20, max = 7000, groups = Create.class)
    private String description;
    @NotBlank(groups = Create.class)
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", groups = Create.class)
    private String eventDate;
    @Embedded
    private Event.Location location;
    private Boolean paid = false;
    private int participantLimit = 0;
    private boolean requestModeration;
    @NotBlank(groups = Create.class)
    @Size(min = 3, max = 120, groups = Create.class)
    private String title;

    @Embeddable
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