package ru.practicum.events.dto;

import lombok.*;
import ru.practicum.categories.model.Category;
import ru.practicum.events.model.State;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
public class EventFullDto {
    private String annotation;

    private Category category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private int id;
    private User initiator;
    private ru.practicum.events.model.Event.Location location;
    private Boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private boolean title;
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
