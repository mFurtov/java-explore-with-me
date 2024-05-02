package ru.practicum.events.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.events.dto.*;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto postEvent(int userId, NewEventDto newEventDto);

    List<EventShortDto> getEventByUser(int id, Pageable sort);

    Event getEventByUserNDto(int eventId);

    EventFullDto getEventByUserId(int userId, int eventId);

    EventFullDto patchEventByUserId(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest);

    EventFullDto patchEventAdmin(int eventId, UpdateEventAdminRequest updateEventAdminRequest);

    EventRequestStatusUpdateResult patchRequestStatus(int userId, int eventId, EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequestDto);

    List<ParticipationRequestDto> getAllRequest(int userId, int eventId);

    List<EventFullDto> getAllEventsInParam(List<Integer> users, List<State> states, List<Integer> categories, String rangeStart, String rangeEnd, Pageable pageable);

    List<EventShortDto> findEventToParams(String text, List<Integer> category, Boolean paid, LocalDateTime start, LocalDateTime end, Boolean onlyAvailable, String order, int from, int size);

    EventFullDto getEventById(int id);
}
