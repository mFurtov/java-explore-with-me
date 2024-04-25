package ru.practicum.users.service;

import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;

import org.springframework.data.domain.Pageable;
import ru.practicum.users.model.User;

import java.util.List;

public interface UserService {
    UserDto postUser(NewUserRequest user);
    List<UserDto> getUser(List<Integer> ids, Pageable sort);
    void dellUser(int id);
    User getUserNDto(int userId);
}
