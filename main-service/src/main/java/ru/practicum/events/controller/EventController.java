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

    private final RequestService requestService;

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@PathVariable int userId, @Validated(Create.class) @RequestBody NewEventDto newEventDto) {
        EventFullDto eventFullDto = eventService.postEvent(userId, newEventDto);
        log.info("Добавлен ивент {}", eventFullDto.getTitle());
        return eventFullDto;
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEventByUser(@PathVariable int userId, @RequestParam(defaultValue = "0") @PositiveOrZero int from, @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<EventShortDto> eventShortDtos = eventService.getEventByUser(userId, PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, "id")));
        log.info("Выведн список ивентов размером {}", eventShortDtos.size());
        return eventShortDtos;
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventByUserIdEvent(@PathVariable int userId, @PathVariable int eventId) {
        EventFullDto eventFullDto = eventService.getEventByUserId(userId, eventId);
        log.info("Выведен ивент {}", eventFullDto.getTitle());
        return eventFullDto;
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto patchEventByUserIdEvent(@PathVariable int userId, @PathVariable int eventId, @Validated(Update.class) @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        EventFullDto eventFullDto = eventService.patchEventByUserId(userId, eventId, updateEventUserRequest);
        log.info("Обновлен ивент {}", eventFullDto.getTitle());
        return eventFullDto;
    }


    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto postRequest(@PathVariable int userId, @RequestParam int eventId) {
        ParticipationRequestDto participationRequestDto = requestService.postRequest(userId, eventId);
        log.info("Добавлена заявка {}", participationRequestDto.getId());
        return participationRequestDto;
    }

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getRequest(@PathVariable int userId) {
        List<ParticipationRequestDto> participationRequestDtoList = requestService.getRequest(userId);
        log.info("Выведен список завявка {}", participationRequestDtoList.size());
        return participationRequestDtoList;
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto pathRequest(@PathVariable int userId, @PathVariable int requestId) {
        ParticipationRequestDto participationRequestDto = requestService.pathRequest(userId, requestId);
        log.info("Завяка закрыта {}", participationRequestDto.getId());
        return participationRequestDto;
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequestStatus(@PathVariable int userId, @PathVariable int eventId, @RequestBody EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequestDto) {
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = eventService.patchRequestStatus(userId, eventId, eventRequestStatusUpdateRequestDto);
        log.info("Изменение статуса заявок на участие в событии {}", eventId);
        return eventRequestStatusUpdateResult;
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getAllEventsInParam(@RequestParam(required = false) List<Integer> users, @RequestParam(required = false) List<State> states, @RequestParam(required = false) List<Integer> categories, @RequestParam(required = false) String rangeStart, @RequestParam(required = false) String rangeEnd, @RequestParam(defaultValue = "0") @PositiveOrZero int from, @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<EventFullDto> eventFullDtos = eventService.getAllEventsInParam(users, states, categories, rangeStart, rangeEnd, PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, "id")));
        log.info("Выведен список ивентов размером", eventFullDtos.size());
        return eventFullDtos;
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto patchEventAdmin(@PathVariable int eventId, @Validated(Update.class) @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        EventFullDto eventFullDto = eventService.patchEventAdmin(eventId, updateEventAdminRequest);
        log.info("Обновлено событие {}", eventFullDto.getId());
        return eventFullDto;
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getAllRequest(@PathVariable int userId, @PathVariable int eventId) {
        List<ParticipationRequestDto> participationRequestDtoList = eventService.getAllRequest(userId, eventId);
        log.info("Получение информации о запросах на участие в событии список размером {}", participationRequestDtoList.size());
        return participationRequestDtoList;
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
        List<EventShortDto> eventShortDtos = eventService.findEventToParams(httpServletRequest, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        log.info("Поиск ивента по параметрам, выведен список размером {}", eventShortDtos.size());
        return eventShortDtos;
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEventById(HttpServletRequest httpServletRequest, @PathVariable int id) {
        EventFullDto eventFullDto = eventService.getEventById(httpServletRequest, id);
        log.info("Выведен ивент {}", eventFullDto.getTitle());
        return eventFullDto;
    }

    @PatchMapping("/users/{userId}/events/{eventId}/rate")
    public EventShortDto patchRate(@PathVariable int userId, @PathVariable int eventId, @RequestParam String grade) {
        return eventService.patchRate(userId, eventId, grade);
    }

    @GetMapping("/users/rate")
    public List<EventShortDto> getRateByParam(@RequestParam(defaultValue = "high") String by, @RequestParam(required = false) List<Integer> grade, @RequestParam(defaultValue = "0") @PositiveOrZero int from, @RequestParam(defaultValue = "10") @Min(1) int size) {
        return eventService.getRateByParam(by, grade, from, size);
    }
}
