package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.service.CategoryService;
import ru.practicum.events.dao.EventRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;
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
        eventRepository.save(event);
        return null;
    }
}
