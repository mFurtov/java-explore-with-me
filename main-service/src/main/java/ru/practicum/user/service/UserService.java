package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto postUser(NewUserRequest user);
    List<UserDto> getUser(List<Integer> ids, Pageable sort);
    void dellUser(int id);
}
