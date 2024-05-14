package ru.practicum.users.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.users.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    default User findByIdOrThrow(Integer id) {
        Optional<User> user = findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("User with id=%d was not found", id), HttpStatus.NOT_FOUND);
        } else {
            return user.get();
        }
    }

    List<User> findByIdIn(List<Integer> ids, Pageable sort);

}
