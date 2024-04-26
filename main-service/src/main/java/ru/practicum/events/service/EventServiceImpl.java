package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.service.CategoryService;
import ru.practicum.events.dao.EventRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.UpdateEventUserRequest;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    @Override
    public EventFullDto postEvent(int userId, NewEventDto newEventDto) {
        User user =userService.getUserNDto(userId);
        Category category = categoryService.getCategoryNDtoById(newEventDto.getCategory());
        Event event = new Event(newEventDto.getAnnotation(),category,newEventDto.getDescription(),newEventDto.getEventDate(),user,newEventDto.getLocation(),newEventDto.getPaid(),newEventDto.getParticipantLimit(),newEventDto.isRequestModeration(),newEventDto.getTitle());
        return EventMapper.mapEventFullFromEvent(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> getEventByUser(int id, Pageable sort) {
        return EventMapper.mapEventShortFromEventToList(eventRepository.findByInitiatorId(id, sort));
    }

    @Override
    public EventFullDto getEventByUserId(int userId, int eventId ) {
        Event event = eventRepository.findByInitiatorIdAndId(userId,eventId);
        if (event != null) {
            return EventMapper.mapEventFullFromEvent(event);
        }else{
            throw new EmptyResultDataAccessException(String.format("Event with id=%d was not found",eventId) ,1);
        }
    }

    @Override
    public EventFullDto patchEventByUserId(int userId, UpdateEventUserRequest updateEventUserRequest) {
       Event event =
    }
}
