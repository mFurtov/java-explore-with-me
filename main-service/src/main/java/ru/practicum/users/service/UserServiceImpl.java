package ru.practicum.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.mapper.UserMapper;

import org.springframework.data.domain.Pageable;
import ru.practicum.users.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto postUser(NewUserRequest user) {
        return UserMapper.mapUserDtoFromUser(userRepository.save(UserMapper.mapUserFromNewUser(user)));
    }

    @Override
    public List<UserDto> getUser(List<Integer> ids, Pageable sort) {
        if (ids == null) {
            return UserMapper.mapUserDtoFromUserToList(userRepository.findAll(sort));
        } else {
            return UserMapper.mapUserDtoFromUserToList(userRepository.findByIdIn(ids, sort));
        }
    }
    public User getUserNDto(int userId) {
        return userRepository.getById(userId);
    }

        public void dellUser(int id) {
        userRepository.findById(id);
        userRepository.deleteById(id);
    }
}
