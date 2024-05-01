package ru.practicum.events.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.events.dto.ParticipationRequestDto;
import ru.practicum.events.model.Request;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RequestMapper {
    public ParticipationRequestDto mapToRequestDtoFromRequest(Request request){
        return new ParticipationRequestDto(request.getCreated(), request.getEvent().getId(), request.getId(),request.getRequester().getId(),request.getStatus());
    }
    public List<ParticipationRequestDto> mapToRequestDtoFromRequestList(Iterable<Request> iterable){
         List<ParticipationRequestDto> result = new ArrayList<>();
        for (Request request : iterable) {
            result.add(mapToRequestDtoFromRequest(request));
        }
        return result;
    }
}
