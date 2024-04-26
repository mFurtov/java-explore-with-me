package ru.practicum.events.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    List<Event> findByInitiatorId(int id, Pageable sort);
    Event findByInitiatorIdAndId(int usrId, int eventId );
}
