package ru.practicum.events.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestsRepository extends JpaRepository<Request,Integer> {
    default Request findByIdOrThrow(Integer id) {
        Optional<Request> request = findById(id);
        if (request.isEmpty()) {
            throw new EmptyResultDataAccessException(String.format("Request with id=%d was not found",id) ,1);
        }else {
            return request.get();
        }
    }
    List<Request> findByRequesterId (int userId);

    List<Request> findAllByEvent (Event event);
}
