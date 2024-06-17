package ru.practicum.events.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.users.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String eventDate;
    private int id;
    private UserDto initiator;
    private Boolean paid;
    private String title;
    private int views;
    private int rate;

    public EventShortDto(String annotation, CategoryDto category, int confirmedRequests, LocalDateTime eventDate, int id, UserDto initiator, Boolean paid, String title, int views, int rate) {
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.eventDate = formatData(eventDate);
        this.id = id;
        this.initiator = initiator;
        this.paid = paid;
        this.title = title;
        this.views = views;
        this.rate = rate;
    }

    private String formatData(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
