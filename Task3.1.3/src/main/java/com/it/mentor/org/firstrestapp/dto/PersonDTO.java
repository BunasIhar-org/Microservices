package com.it.mentor.org.firstrestapp.dto;

import com.it.mentor.org.firstrestapp.models.*;
import lombok.*;
import javax.validation.constraints.*;
import java.util.*;

@Getter
@Setter
public class PersonDTO {

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной от 2 до 100 символов")
    private String username;

    @NotEmpty(message = "Email не должен быть пустым")
    @Email(message = "Email должен быть валидным")
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    @Size(min = 1, message = "У пользователя должна быть хотя бы одна роль")
    private Set<Role> roles;
}