package ru.practicum.user.dto;

import lombok.Data;
import ru.practicum.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewUserRequest {
    @Email
    @NotBlank
    @Size(min = 1, max = 255, groups = Create.class)
    private String email;
    @NotBlank
    @Size(min = 1, max = 255, groups = Create.class)
    private String name;
}
