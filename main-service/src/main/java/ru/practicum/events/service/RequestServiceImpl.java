package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
    private final EventService eventService;

    @Override
    public ParticipationRequestDto postRequest(int userId, int eventId) {
        User user = userService.getUserNDto(userId);
        Event event = eventService.getEventByUserNDto(eventId);
        return RequestMapper.mapToRequestDtoFromRequest(requestsRepository.save(new Request(event, user)));
    }

    @Override
    public List<ParticipationRequestDto> getRequest(int userId) {
        return RequestMapper.mapToRequestDtoFromRequestList(requestsRepository.findByRequesterId(userId));
    }

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
