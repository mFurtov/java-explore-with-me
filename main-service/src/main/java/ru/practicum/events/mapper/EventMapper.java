package ru.practicum.events.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.categories.mapper.CategoryMapper;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;
import ru.practicum.users.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EventMapper {
    public EventShortDto mapEventShortFromEvent(Event event) {
        return new EventShortDto(event.getAnnotation(), CategoryMapper.mapCategoryDtoFromCategory(event.getCategory()), event.getConfirmedRequests(), event.getEventDate(), event.getId(), UserMapper.mapUserDtoFromUser(event.getInitiator()), event.getPaid(), event.getTitle(), event.getViews());
    }

    public List<EventShortDto> mapEventShortFromEventToList(Iterable<Event> event) {
        List<EventShortDto> result = new ArrayList<>();
        for (Event e : event) {
            result.add(mapEventShortFromEvent(e));
        }
        return result;
    }

    public List<EventFullDto> mapEventFullFromEventToList(Iterable<Event> event) {
        List<EventFullDto> result = new ArrayList<>();
        for (Event e : event) {
            result.add(mapEventFullFromEvent(e));
        }
        return result;
    }

    public EventFullDto mapEventFullFromEvent(Event event) {
        return new EventFullDto(event.getAnnotation(), CategoryMapper.mapCategoryDtoFromCategory(event.getCategory()), event.getConfirmedRequests(), event.getCreatedOn(), event.getDescription(), event.getEventDate(), event.getId(), UserMapper.mapUserShortFromUser(event.getInitiator()), event.getLocation(), event.getPaid(), event.getParticipantLimit(), event.getPublishedOn(), event.getRequestModeration(), event.getState(), event.getTitle(), event.getViews());
    }
}
