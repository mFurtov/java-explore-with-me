package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.dao.EventRepository;
import ru.practicum.events.dto.ParticipationRequestDto;
import ru.practicum.events.mapper.RequestMapper;
import ru.practicum.events.model.Request;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;
import ru.practicum.exception.ConflictException;
import ru.practicum.events.dao.RequestsRepository;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestsRepository requestsRepository;
    private final UserService userService;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public ParticipationRequestDto postRequest(int userId, int eventId) {
        User user = userService.getUserNDto(userId);
        Event event = eventRepository.findByIdOrThrow(eventId);
        if (requestsRepository.findByRequesterIdAndEventId(userId, eventId) != null) {
            throw new ConflictException("You cannot add a repeat request", HttpStatus.CONFLICT);
        }
        if (event.getParticipantLimit() == 0) {
            return RequestMapper.mapToRequestDtoFromRequest(requestsRepository.save(new Request(event, user, State.CONFIRMED)));
        }
        if (event.getInitiator().getId() == userId || !event.getState().equals(State.PUBLISHED) || event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ConflictException("the initiator of the event cannot add a request to participate in his event; it is impossible to participate in an unpublished event; if the event has reached the limit of requests for participation, an error must be returned", HttpStatus.CONFLICT);
        }
        if (!event.getRequestModeration()) {
            event.setConfirmedRequests(+1);
            eventRepository.save(event);
            return RequestMapper.mapToRequestDtoFromRequest(requestsRepository.save(new Request(event, user, State.CONFIRMED)));
        }
        return RequestMapper.mapToRequestDtoFromRequest(requestsRepository.save(new Request(event, user)));
    }

    @Transactional
    @Override
    public List<ParticipationRequestDto> getRequest(int userId) {
        userService.getUserNDto(userId);
        return RequestMapper.mapToRequestDtoFromRequestList(requestsRepository.findByRequesterId(userId));
    }

    @Transactional
    @Override
    public ParticipationRequestDto pathRequest(int userId, int requestId) {
        Request request = requestsRepository.findByIdOrThrow(requestId);
        if (request.getRequester().getId() == userId) {
            request.setStatus(State.CANCELED);
            return RequestMapper.mapToRequestDtoFromRequest(requestsRepository.save(request));
        } else {
            throw new ConflictException("Request user is not owner", HttpStatus.CONFLICT);
        }

    }

}
