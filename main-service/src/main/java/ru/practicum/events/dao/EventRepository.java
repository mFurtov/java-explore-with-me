package ru.practicum.events.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;
import ru.practicum.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    default Event findByIdOrThrow(Integer id) {
        Optional<Event> event = findById(id);
        if (event.isEmpty()) {
            throw new NotFoundException(String.format("Event with id=%d was not found", id), HttpStatus.NOT_FOUND);
        } else {
            return event.get();
        }
    }

    default Event findByIdOrThrowPublished(Integer id) {
        Optional<Event> event = findEventById(id);
        if (event.isEmpty()) {
            throw new NotFoundException(String.format("Event with id=%d was not found", id), HttpStatus.NOT_FOUND);
        } else {
            return event.get();
        }
    }

    List<Event> findByInitiatorId(int id, Pageable sort);

    Event findByInitiatorIdAndId(int usrId, int eventId);

    List<Event> findEventsByInitiatorIdInAndStateInAndCategoryIdInAndEventDateGreaterThanEqualAndEventDateLessThanEqual(List<Integer> users, List<State> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE (e.annotation LIKE %?1% OR e.description LIKE %?1%) AND e.category.id IN ?2 AND e.paid = ?3 AND e.eventDate >= ?4 AND e.eventDate <= ?5 AND e.state = 'PUBLISHED' ")
    List<Event> findEventToParams(String text, List<Integer> category, Boolean paid, LocalDateTime start, LocalDateTime end, Pageable sort);

    @Query("SELECT e FROM Event e WHERE (e.annotation LIKE %?1% OR e.description LIKE %?1%) AND e.category.id IN ?2 AND e.paid = ?3 AND e.eventDate >= ?4 AND e.eventDate <= ?5 AND e.confirmedRequests < e.participantLimit AND e.state = 'PUBLISHED' ")
    List<Event> findEventToParamsAvailable(String text, List<Integer> category, Boolean paid, LocalDateTime start, LocalDateTime end, Pageable sort);

    @Query("SELECT e FROM Event e where e.id = ?1 AND e.state = 'PUBLISHED'")
    Optional<Event> findEventById(int id);
}
