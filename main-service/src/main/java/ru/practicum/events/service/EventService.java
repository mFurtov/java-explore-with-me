package ru.practicum.events.service;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.NewEventDto;

public interface EventService {
    EventFullDto postEvent (int userId, NewEventDto newEventDto);
}
