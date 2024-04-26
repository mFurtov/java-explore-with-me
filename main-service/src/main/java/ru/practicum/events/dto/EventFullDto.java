package ru.practicum.events.dto;

import lombok.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.model.State;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private String annotation;

    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private int id;
    private UserShortDto initiator;
    private ru.practicum.events.model.Event.Location location;
    private Boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private int views;

    @Embeddable
    @Data
    public static class Location {
        @Column(nullable = false)
        private double lat;

        @Column(nullable = false)
        private double lon;
    }
}
