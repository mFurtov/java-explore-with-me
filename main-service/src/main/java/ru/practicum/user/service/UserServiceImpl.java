package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.user.dao.UserRepository;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;

import org.springframework.data.domain.Pageable;

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

    public void dellUser(int id) {
        userRepository.findById(id);
        userRepository.deleteById(id);
    }
}
