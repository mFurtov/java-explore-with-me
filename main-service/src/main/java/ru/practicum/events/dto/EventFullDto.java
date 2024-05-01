package ru.practicum.events.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private String annotation;

    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private String eventDate;
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

    public EventFullDto(String annotation, CategoryDto category, int confirmedRequests, LocalDateTime createdOn, String description, LocalDateTime eventDate, int id, UserShortDto initiator, Event.Location location, Boolean paid, int participantLimit, LocalDateTime publishedOn, boolean requestModeration, State state, String title, int views) {
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = formatData(eventDate);
        this.id = id;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.views = views;
    }

    private String formatData(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
    @Embeddable
    @Data
    public static class Location {
        @Column(nullable = false)
        private double lat;

        @Column(nullable = false)
        private double lon;
    }
}
