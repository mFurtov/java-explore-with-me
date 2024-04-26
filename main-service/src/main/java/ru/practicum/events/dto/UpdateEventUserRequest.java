package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Create;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;
import ru.practicum.events.validator.DataValid;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000, groups = Create.class)
    private String annotation;

    private int category;
    @Size(min = 20, max = 7000, groups = Create.class)
    private String description;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", groups = Create.class)
    @DataValid(groups = Create.class)
    private String eventDate;
    @Embedded
    private Event.Location location;
    private Boolean paid = false;
    private int participantLimit = 0;
    private boolean requestModeration =true;
    private State stateAction;
    private String title;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private double lat;
        private double lon;
    }
}
