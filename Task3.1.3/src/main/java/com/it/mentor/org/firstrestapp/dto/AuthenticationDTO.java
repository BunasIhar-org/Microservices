package com.it.mentor.org.firstrestapp.dto;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
public class AuthenticationDTO {

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной от 2 до 100 символов")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;
}