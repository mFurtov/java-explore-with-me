package ru.practicum.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Create;
import ru.practicum.Update;
import ru.practicum.events.dto.*;
import ru.practicum.events.model.State;
import ru.practicum.events.service.EventService;
import ru.practicum.events.service.RequestService;
import ru.practicum.pageable.PageableCreate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class EventController {
    private final EventService eventService;

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@PathVariable int userId, @Validated(Create.class) @RequestBody NewEventDto newEventDto) {
        EventFullDto eventFullDto = eventService.postEvent(userId, newEventDto);
        log.info("Добавлен ивент {}", eventFullDto.getTitle());
        return eventService.postEvent(userId, newEventDto);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEventByUser(@PathVariable int userId, @RequestParam(defaultValue = "0") @PositiveOrZero int from, @RequestParam(defaultValue = "10") @Min(1) int size) {
        return eventService.getEventByUser(userId, PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, "id")));
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventByUserIdEvent(@PathVariable int userId, @PathVariable int eventId) {
        return eventService.getEventByUserId(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto patchEventByUserIdEvent(@PathVariable int userId, @PathVariable int eventId, @Validated(Update.class) @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.patchEventByUserId(userId, eventId, updateEventUserRequest);
    }

    private final RequestService requestService;

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto postRequest(@PathVariable int userId, @RequestParam int eventId) {
        return requestService.postRequest(userId, eventId);
    }

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getRequest(@PathVariable int userId) {
        return requestService.getRequest(userId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto pathRequest(@PathVariable int userId, @PathVariable int requestId) {
        return requestService.pathRequest(userId, requestId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequestStatus(@PathVariable int userId, @PathVariable int eventId, @RequestBody EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequestDto) {
        return eventService.patchRequestStatus(userId, eventId, eventRequestStatusUpdateRequestDto);
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getAllEventsInParam(@RequestParam(required = false) List<Integer> users, @RequestParam(required = false) List<State> states, @RequestParam(required = false) List<Integer> categories, @RequestParam(required = false) String rangeStart, @RequestParam(required = false) String rangeEnd, @RequestParam(defaultValue = "0") @PositiveOrZero int from, @RequestParam(defaultValue = "10") @Min(1) int size) {
        return eventService.getAllEventsInParam(users, states, categories, rangeStart, rangeEnd, PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, "id")));
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto patchEventAdmin(@PathVariable int eventId, @Validated(Update.class) @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        EventFullDto eventFullDto = eventService.patchEventAdmin(eventId, updateEventAdminRequest);
        log.info("Обновлено событие {}", eventFullDto.getId());
        return eventFullDto;
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getAllRequest(@PathVariable int userId, @PathVariable int eventId) {
        return eventService.getAllRequest(userId, eventId);
    }

    @GetMapping("/events")
    public List<EventShortDto> getFilteredEvents(HttpServletRequest httpServletRequest,
                                                 @RequestParam(required = false) String text,
                                                 @RequestParam(required = false) List<Integer> categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                 @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return eventService.findEventToParams(httpServletRequest, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEventById(HttpServletRequest httpServletRequest, @PathVariable int id) {
        return eventService.getEventById(httpServletRequest, id);
    }
}
