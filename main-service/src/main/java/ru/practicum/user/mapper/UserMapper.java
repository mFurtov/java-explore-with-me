package ru.practicum.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {
    public UserDto mapUserDtoFromUser(User user) {
        return new UserDto(user.getEmail(), user.getId(), user.getName());
    }

    public List<UserDto> mapUserDtoFromUserToList(Iterable<User> user) {
        List<UserDto> result = new ArrayList<>();
        for (User u : user) {
            result.add(mapUserDtoFromUser(u));
        }
        return result;
    }


    public User mapUserFromNewUser(NewUserRequest user) {
        return new User(user.getEmail(), user.getName());
    }
}
