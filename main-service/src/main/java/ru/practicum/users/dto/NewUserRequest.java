package ru.practicum.users.dto;

import lombok.Data;
import ru.practicum.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewUserRequest {
    @Email(groups = Create.class)
    @NotBlank(groups = Create.class)
    @Size(min = 6, max = 254, groups = Create.class)
    private String email;
    @NotBlank(groups = Create.class)
    @Size(min = 2, max = 250, groups = Create.class)
    private String name;
}
