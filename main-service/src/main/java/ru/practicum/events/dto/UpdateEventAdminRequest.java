package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Create;
import ru.practicum.events.model.Event;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000, groups = Create.class)
    private String annotation;

    private Integer category;
    @Size(min = 20, max = 7000, groups = Create.class)
    private String description;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", groups = Create.class)
    private String eventDate;
    @Embedded
    private Event.Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionAdm stateAction;
    @Size(min = 3, max = 120, groups = Create.class)
    private String title;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private double lat;
        private double lon;
    }
    public enum StateActionAdm {
        PUBLISH_EVENT,
        REJECT_EVENT;
    }
}
