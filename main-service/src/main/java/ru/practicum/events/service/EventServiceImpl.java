package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.StatsClient;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.service.CategoryService;
import ru.practicum.dto.EventStatDto;
import ru.practicum.events.dao.EventRepository;
import ru.practicum.events.dao.RequestsRepository;
import ru.practicum.events.dto.*;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.mapper.RequestMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.EventSpecifications;
import ru.practicum.events.model.Request;
import ru.practicum.events.model.State;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.pageable.PageableCreate;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.events.dto.EventRequestStatusUpdateRequestDto.StateActionEventUpdate.REJECTED;
import static ru.practicum.events.model.State.*;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final RequestsRepository requestsRepository;
    private final StatsClient statClient;

    @Override
    public EventFullDto postEvent(int userId, NewEventDto newEventDto) {
        User user = userService.getUserNDto(userId);
        Category category = categoryService.getCategoryNDtoById(newEventDto.getCategory());
        Event event = new Event(newEventDto.getAnnotation(), category, newEventDto.getDescription(), newEventDto.getEventDate(), user, newEventDto.getLocation(), newEventDto.getPaid(), newEventDto.getParticipantLimit(), newEventDto.getRequestModeration(), newEventDto.getTitle());
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
            throw new NotFoundException(String.format("Event with id=%d was not found", eventId), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public EventFullDto patchEventByUserId(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId);

        if (!event.getState().equals(PENDING) && !event.getState().equals(CANCELED)) {
            throw new ConflictException("Only pending or canceled events can be changed", HttpStatus.CONFLICT);
        }

        if (event != null) {
            if (event.getState() != CANCELED && event.getState() != PENDING) {
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
                        event.setState(PENDING);
                        break;
                    case CANCEL_REVIEW:
                        event.setState(CANCELED);
                        break;
                }

            }
            if (updateEventUserRequest.getTitle() != null) {
                event.setTitle(updateEventUserRequest.getTitle());
            }

            return EventMapper.mapEventFullFromEvent(eventRepository.save(event));
        } else {
            throw new NotFoundException(String.format("Event with id=%d was not found", eventId), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public EventFullDto patchEventAdmin(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventRepository.getById(eventId);

        if (event != null) {
            if (event.getState().equals(CANCELED)) {
                throw new ConflictException("Cannot publish the event because it's not in the right state: CANCELED", HttpStatus.CONFLICT);

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
                        if (event.getState().equals(PUBLISHED)) {
                            throw new ConflictException("Cannot publish the event because it's not in the right state: PUBLISHED", HttpStatus.CONFLICT);
                        }
                        event.setState(State.PUBLISHED);
                        break;
                    case REJECT_EVENT:
                        if (event.getState().equals(PUBLISHED)) {
                            throw new ConflictException("Cannot publish the event because it's not in the right state: PUBLISHED", HttpStatus.CONFLICT);
                        }
                        event.setState(CANCELED);
                        break;
                }

            }
            if (updateEventAdminRequest.getTitle() != null) {
                event.setTitle(updateEventAdminRequest.getTitle());
            }

            return EventMapper.mapEventFullFromEvent(eventRepository.save(event));
        } else {
            throw new NotFoundException(String.format("Event with id=%d was not found for user with id=%d", eventId), HttpStatus.NOT_FOUND);
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
            requestsToApprove.forEach(req -> {
                if (req.getStatus().equals(CONFIRMED)) {
                    throw new ConflictException("Cannot cancel already confirmed request", HttpStatus.CONFLICT);
                }
                req.setStatus(State.REJECTED);
            });
            requestsRepository.saveAll(requestsToApprove);
            eventRequestStatusUpdateResult.setRejectedRequests(RequestMapper.mapToRequestDtoFromRequestList(requestsToApprove));
            return eventRequestStatusUpdateResult;
        }

        int maxApprovedRequests = event.getParticipantLimit();


        for (int i = 0; i < requestsToApprove.size(); i++) {
            Request req = requestsToApprove.get(i);
            if (!req.getStatus().equals(PENDING)) {
                throw new ConflictException("Request must have status PENDING", HttpStatus.BAD_REQUEST);
            }
            if (i < maxApprovedRequests) {
                req.setStatus(State.CONFIRMED);
                eventRequestStatusUpdateResult.getConfirmedRequests().add(RequestMapper.mapToRequestDtoFromRequest(req));
                event.setConfirmedRequests(+1);
            } else {
                req.setStatus(State.REJECTED);
                eventRequestStatusUpdateResult.getRejectedRequests().add(RequestMapper.mapToRequestDtoFromRequest(req));
            }
        }
        eventRepository.save(event);
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
        Specification<Event> spec = Specification.where(EventSpecifications.withUsers(users))
                .and(EventSpecifications.withStates(states))
                .and(EventSpecifications.withCategories(categories))
                .and(EventSpecifications.withDateRange(formatterData(rangeStart), formatterData(rangeEnd)));

        Page<Event> page = eventRepository.findAll(spec, pageable);
        List<Event> event = page.getContent();
        return EventMapper.mapEventFullFromEventToList(event);
    }

    @Override
    public List<EventShortDto> findEventToParams(HttpServletRequest httpServletRequest, String text, List<Integer> category, Boolean paid, LocalDateTime start, LocalDateTime end, Boolean onlyAvailable, String order, int from, int size) {
        if (order == null) {
            order = "id";
        } else if (order.equalsIgnoreCase("EVENT_DATE")) {
            order = "eventDate";
        } else {
            order = "views";
        }
        if (start == null) {
            start = LocalDateTime.now();
            end = start.plusYears(100);
        }
        if (start.isAfter(end)) {
            throw new BadRequestException("The start date cannot be earlier than the finish", HttpStatus.BAD_REQUEST);
        }
        Specification<Event> spec = Specification.where(EventSpecifications.withText(text))
                .and(EventSpecifications.withCategories(category))
                .and(EventSpecifications.withPaid(paid))
                .and(EventSpecifications.withDateRange(start, end));

        List<Event> event;
        if (onlyAvailable) {
            Page<Event> page = eventRepository.findAll(spec, PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, order)));
            event = page.getContent();
        } else {
            Page<Event> page = eventRepository.findAll(spec, PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, order)));
            event = page.getContent();
        }

        event.forEach(e -> {
            e.setViews(e.getViews() + 1);
            eventRepository.save(e);
        });
        postStat(httpServletRequest);
        return EventMapper.mapEventShortFromEventToList(event);
    }

    @Override
    public EventFullDto getEventById(HttpServletRequest httpServletRequest, int id) {
        Event event = eventRepository.findByIdOrThrowPublished(id);
        event.setViews(+1);
        eventRepository.save(event);
        postStat(httpServletRequest);
        return EventMapper.mapEventFullFromEvent(event);
    }


    private LocalDateTime formatterData(String dataTime) {
        if (dataTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dataTime, formatter);
        } else {
            return null;
        }
    }

    public void postStat(HttpServletRequest httpServletRequest) {
        statClient.post(EventStatDto.builder()
                .ip(httpServletRequest.getRemoteAddr())
                .app("ewm-main-service")
                .uri(httpServletRequest.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());
    }
}
