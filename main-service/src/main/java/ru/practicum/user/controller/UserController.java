package ru.practicum.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Create;
import ru.practicum.pageable.PageableCreate;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto postUser(@RequestBody @Validated(Create.class) NewUserRequest userRequest){
        UserDto userDto = userService.postUser(userRequest);
        log.info("Добавлен пользователь с id {}",userDto.getId());
        return userDto;
    }
    @GetMapping

    public List<UserDto> getUser(@RequestParam(required = false) List<Integer> ids, @RequestParam(defaultValue = "0") @PositiveOrZero int from, @RequestParam(defaultValue = "10") @Min(1) int size){
       return userService.getUser(ids, PageableCreate.getPageable(from,size, Sort.by(Sort.Direction.DESC, "id")));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dellUser(@PathVariable int id) {
        userService.dellUser(id);
        log.info("Пользователь с id \"{}\" удален", id);
    }
}
