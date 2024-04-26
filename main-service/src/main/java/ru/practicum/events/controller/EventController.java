package ru.practicum.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Create;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.UpdateEventUserRequest;
import ru.practicum.events.service.EventService;
import ru.practicum.pageable.PageableCreate;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService eventService;

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@PathVariable int userId,@Validated(Create.class) @RequestBody NewEventDto newEventDto) {
        return eventService.postEvent(userId, newEventDto);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEventByUser (@PathVariable int userId, @RequestParam(defaultValue = "0") @PositiveOrZero int from, @RequestParam(defaultValue = "10") @Min(1) int size){
        return eventService.getEventByUser(userId, PageableCreate.getPageable(from,size, Sort.by(Sort.Direction.ASC,"id")));
    }
    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventByUserIdEvent (@PathVariable int userId,@PathVariable int eventId) {
        return eventService.getEventByUserId(userId, eventId);
    }
    @PatchMapping
    public EventFullDto patchEventByUserIdEvent (@PathVariable int userId, @RequestBody @Validated(Create.class)UpdateEventUserRequest updateEventUserRequest) {
        return eventService.patchEventByUserId(userId, updateEventUserRequest);
    }
}
