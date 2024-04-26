package ru.practicum.events.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.UpdateEventUserRequest;

import java.util.List;

public interface EventService {
    EventFullDto postEvent (int userId, NewEventDto newEventDto);
    List<EventShortDto> getEventByUser(int id, Pageable sort);
    EventFullDto getEventByUserId(int userId, int eventId);

    EventFullDto patchEventByUserId(int userId, UpdateEventUserRequest updateEventUserRequest);
}
