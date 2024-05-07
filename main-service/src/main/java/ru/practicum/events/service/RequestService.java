package ru.practicum.events.service;

import ru.practicum.events.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto postRequest(int userId, int eventId);

    List<ParticipationRequestDto> getRequest(int userId);

    ParticipationRequestDto pathRequest(int userId, int requestId);
}
