package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.users.dto.UserDto;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime eventDate;
    private int id;
    private UserDto initiator;
    private Boolean paid;
    private String title;
    private int views;
}
