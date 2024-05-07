package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Update;
import ru.practicum.events.model.Event;
import ru.practicum.events.validator.DataValid;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000, groups = Update.class)
    private String annotation;
    private Integer category;
    @Size(min = 20, max = 7000, groups = Update.class)
    private String description;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", groups = Update.class)
    @DataValid(groups = Update.class)
    private String eventDate;
    private Event.Location location;
    private Boolean paid;
    @Positive(groups = Update.class)
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionUser stateAction;
    @Size(min = 3, max = 120, groups = Update.class)
    private String title;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private double lat;
        private double lon;
    }

    public enum StateActionUser {
        SEND_TO_REVIEW,
        CANCEL_REVIEW;
    }

}
