package ru.practicum.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.service.EventService;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/users/{userId}/events")
    public void postEvent(@PathVariable int userId, @RequestBody NewEventDto newEventDto) {
        eventService.postEvent(userId, newEventDto);
    }
}
