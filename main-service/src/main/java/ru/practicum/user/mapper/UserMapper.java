package ru.practicum.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

@UtilityClass
public class UserMapper {
    public UserDto mapUserDtoFromUser(User user) {
        return new UserDto(user.getEmail(), user.getId(), user.getName());
    }

    public User mapUserFromNewUser(NewUserRequest user) {
        return new User(user.getEmail(), user.getName());
    }
}
