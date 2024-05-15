package ru.practicum.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.ConflictException;
import ru.practicum.pageable.PageableCreate;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.mapper.UserMapper;

import org.springframework.data.domain.Pageable;
import ru.practicum.users.model.User;
import ru.practicum.users.model.UserSpecifications;

import java.util.List;
import java.util.Locale;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto postUser(NewUserRequest user) {
        try {
            return UserMapper.mapUserDtoFromUser(userRepository.save(UserMapper.mapUserFromNewUser(user)));
        } catch (DataIntegrityViolationException e) {
            log.info("Нарушение уникальности данных {}", user.getName());
            throw new ConflictException(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @Transactional
    @Override
    public List<UserDto> getUser(List<Integer> ids, Pageable sort) {
        if (ids == null) {
            return UserMapper.mapUserDtoFromUserToList(userRepository.findAll(sort));
        } else {
            return UserMapper.mapUserDtoFromUserToList(userRepository.findByIdIn(ids, sort));
        }
    }

    @Transactional
    @Override
    public User getUserNDto(int userId) {
        return userRepository.findByIdOrThrow(userId);
    }

    @Override
    public User saveUserNDto(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserDto> getUserRate(String by, List<Integer> grade, int from, int size) {
        Sort.Direction direction;
        switch (by.toLowerCase(Locale.ROOT)) {
            case "high":
                direction = DESC;
                break;
            case "low":
                direction = ASC;
                break;
            default:
                throw new BadRequestException("Invalid filtering parameter specified", HttpStatus.CONFLICT);
        }
        Specification<User> spec = Specification.where(UserSpecifications.withRates(grade));
        Page<User> page = userRepository.findAll(spec, PageableCreate.getPageable(from, size, Sort.by(direction, "rate")));
        return UserMapper.mapUserDtoFromUserToList(page.getContent());
    }

    @Transactional
    @Override
    public void dellUser(int id) {
        userRepository.findByIdOrThrow(id);
        userRepository.deleteById(id);
    }
}
