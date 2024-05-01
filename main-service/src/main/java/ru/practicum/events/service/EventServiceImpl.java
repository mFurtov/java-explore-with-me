package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.service.CategoryService;
import ru.practicum.events.dao.EventRepository;
import ru.practicum.events.dao.RequestsRepository;
import ru.practicum.events.dto.*;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.mapper.RequestMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Request;
import ru.practicum.events.model.State;
import ru.practicum.exception.ConflictException;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.events.dto.EventRequestStatusUpdateRequestDto.StateActionEventUpdate.REJECTED;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final RequestsRepository requestsRepository;

    @Override
    public EventFullDto postEvent(int userId, NewEventDto newEventDto) {
        User user = userService.getUserNDto(userId);
        Category category = categoryService.getCategoryNDtoById(newEventDto.getCategory());
        Event event = new Event(newEventDto.getAnnotation(), category, newEventDto.getDescription(), newEventDto.getEventDate(), user, newEventDto.getLocation(), newEventDto.getPaid(), newEventDto.getParticipantLimit(), newEventDto.isRequestModeration(), newEventDto.getTitle());
        return EventMapper.mapEventFullFromEvent(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> getEventByUser(int id, Pageable sort) {
        return EventMapper.mapEventShortFromEventToList(eventRepository.findByInitiatorId(id, sort));
    }

    @Override
    public Event getEventByUserNDto(int eventId) {
        return eventRepository.getById(eventId);
    }

    @Override
    public EventFullDto getEventByUserId(int userId, int eventId) {
        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId);
        if (event != null) {
            return EventMapper.mapEventFullFromEvent(event);
        } else {
            throw new EmptyResultDataAccessException(String.format("Event with id=%d was not found", eventId), 1);
        }
    }

    @Override
    public EventFullDto patchEventByUserId(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId);


        if (event != null) {
            if (event.getState() != State.CANCELED && event.getState() != State.PENDING) {
                throw new ConflictException("Event state is not eligible for modification", HttpStatus.CONFLICT);
            }

            LocalDateTime twoHoursFromNow = LocalDateTime.now().plusHours(2);
            if (updateEventUserRequest.getEventDate() != null && formatterData(updateEventUserRequest.getEventDate()).isBefore(twoHoursFromNow)) {
                throw new ConflictException("Event date and time cannot be earlier than two hours from now", HttpStatus.CONFLICT);
            }

            if (updateEventUserRequest.getAnnotation() != null) {
                event.setAnnotation(updateEventUserRequest.getAnnotation());
            }
            if (updateEventUserRequest.getCategory() != null) {
                Category category = categoryService.getCategoryNDtoById(updateEventUserRequest.getCategory());
                event.setCategory(category);
            }
            if (updateEventUserRequest.getDescription() != null) {
                event.setDescription(updateEventUserRequest.getDescription());
            }
            if (updateEventUserRequest.getEventDate() != null) {
                event.setEventDate(formatterData(updateEventUserRequest.getEventDate()));
            }
            if (updateEventUserRequest.getLocation() != null) {
                event.setLocation(updateEventUserRequest.getLocation());
            }
            if (updateEventUserRequest.getPaid() != null) {
                event.setPaid(updateEventUserRequest.getPaid());
            }
            if (updateEventUserRequest.getParticipantLimit() != null) {
                event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
            }
            if (updateEventUserRequest.getRequestModeration() != null) {
                event.setRequestModeration(updateEventUserRequest.getRequestModeration());
            }
            if (updateEventUserRequest.getStateAction() != null) {
                switch (updateEventUserRequest.getStateAction()) {
                    case SEND_TO_REVIEW:
                        event.setState(State.PENDING);
                        break;
                    case CANCEL_REVIEW:
                        event.setState(State.CANCELED);
                        break;
                }

            }
            if (updateEventUserRequest.getTitle() != null) {
                event.setTitle(updateEventUserRequest.getTitle());
            }

            return EventMapper.mapEventFullFromEvent(eventRepository.save(event));
        } else {
            throw new EmptyResultDataAccessException(String.format("Event with id=%d was not found for user with id=%d", eventId, userId), 1);
        }
    }

    @Override
    public EventFullDto patchEventAdmin(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventRepository.getById(eventId);


        if (event != null) {
            if (event.getState() != State.CANCELED && event.getState() != State.PENDING) {
                throw new ConflictException("Event state is not eligible for modification", HttpStatus.CONFLICT);
            }

            LocalDateTime twoHoursFromNow = LocalDateTime.now().plusHours(2);
            if (updateEventAdminRequest.getEventDate() != null && formatterData(updateEventAdminRequest.getEventDate()).isBefore(twoHoursFromNow)) {
                throw new ConflictException("Event date and time cannot be earlier than two hours from now", HttpStatus.CONFLICT);
            }

            if (updateEventAdminRequest.getAnnotation() != null) {
                event.setAnnotation(updateEventAdminRequest.getAnnotation());
            }
            if (updateEventAdminRequest.getCategory() != null) {
                Category category = categoryService.getCategoryNDtoById(updateEventAdminRequest.getCategory());
                event.setCategory(category);
            }
            if (updateEventAdminRequest.getDescription() != null) {
                event.setDescription(updateEventAdminRequest.getDescription());
            }
            if (updateEventAdminRequest.getEventDate() != null) {
                event.setEventDate(formatterData(updateEventAdminRequest.getEventDate()));
            }
            if (updateEventAdminRequest.getLocation() != null) {
                event.setLocation(updateEventAdminRequest.getLocation());
            }
            if (updateEventAdminRequest.getPaid() != null) {
                event.setPaid(updateEventAdminRequest.getPaid());
            }
            if (updateEventAdminRequest.getParticipantLimit() != null) {
                event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
            }
            if (updateEventAdminRequest.getRequestModeration() != null) {
                event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
            }
            if (updateEventAdminRequest.getStateAction() != null) {
                switch (updateEventAdminRequest.getStateAction()) {
                    case PUBLISH_EVENT:
                        event.setState(State.PUBLISHED);
                        break;
                    case REJECT_EVENT:
                        event.setState(State.CANCELED);
                        break;
                }

            }
            if (updateEventAdminRequest.getTitle() != null) {
                event.setTitle(updateEventAdminRequest.getTitle());
            }

            return EventMapper.mapEventFullFromEvent(eventRepository.save(event));
        } else {
            throw new EmptyResultDataAccessException(String.format("Event with id=%d was not found for user with id=%d", eventId), 1);
        }
    }

    public EventRequestStatusUpdateResult patchRequestStatus(int userId, int eventId, EventRequestStatusUpdateRequestDto request) {

        Event event = getEventByUserNDto(eventId);
        List<Request> requestsToApprove = requestsRepository.findAllById(request.getRequestIds());
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            return eventRequestStatusUpdateResult;
        }
        List<Request> requests = requestsRepository.findAllByEvent(event);
        int confRequests = (int) requests.stream().filter(r -> r.getStatus().equals(State.CONFIRMED)).count();

        if (request.getStatus().equals(EventRequestStatusUpdateRequestDto.StateActionEventUpdate.CONFIRMED)) {
            int requestedRequestsCount = request.getRequestIds().size();
            int availableSlots = event.getParticipantLimit() - confRequests;

            if (requestedRequestsCount > availableSlots) {
                throw new ConflictException("The participant limit has been reached", HttpStatus.CONFLICT);
            }
        }

        if (request.getStatus().equals(REJECTED)) {
            requestsToApprove.forEach(req -> req.setStatus(State.REJECTED));
            requestsRepository.saveAll(requestsToApprove);
            eventRequestStatusUpdateResult.setRejectedRequests(RequestMapper.mapToRequestDtoFromRequestList(requestsToApprove));
            return eventRequestStatusUpdateResult;
        }

        int maxApprovedRequests = event.getParticipantLimit();


        for (int i = 0; i < requestsToApprove.size(); i++) {
            Request req = requestsToApprove.get(i);
            if (!req.getStatus().equals(State.PENDING)) {
                throw new ConflictException("Request must have status PENDING", HttpStatus.BAD_REQUEST);
            }
            if (i < maxApprovedRequests) {
                req.setStatus(State.CONFIRMED);
                eventRequestStatusUpdateResult.getConfirmedRequests().add(RequestMapper.mapToRequestDtoFromRequest(req));
            } else {
                req.setStatus(State.REJECTED);
                eventRequestStatusUpdateResult.getRejectedRequests().add(RequestMapper.mapToRequestDtoFromRequest(req));
            }
        }

        requestsRepository.saveAll(requests);
        return eventRequestStatusUpdateResult;

    }

    @Override
    public List<ParticipationRequestDto> getAllRequest(int userId, int eventId) {
        Event event = eventRepository.getById(eventId);
        return RequestMapper.mapToRequestDtoFromRequestList(requestsRepository.findAllByEvent(event));
    }

    @Override
    public List<EventFullDto> getAllEventsInParam(List<Integer> users, List<State> states, List<Integer> categories, String rangeStart, String rangeEnd, Pageable pageable) {
        return EventMapper.mapEventFullFromEventToList(eventRepository.findEventsByInitiatorIdInAndStateInAndCategoryIdInAndEventDateGreaterThanEqualAndEventDateLessThanEqual(users, states, categories, formatterData(rangeStart), formatterData(rangeEnd), pageable));
    }


    private LocalDateTime formatterData(String dataTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dataTime, formatter);
    }
}
