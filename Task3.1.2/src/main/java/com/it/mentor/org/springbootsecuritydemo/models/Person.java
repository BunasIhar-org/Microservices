package com.it.mentor.org.springbootsecuritydemo.models;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_security")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной от 2 до 100 символов")
    private String username;

    @NotEmpty(message = "Email не должен быть пустым")
    @Email(message = "Email должен быть валидным")
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    @Size(min = 1, message = "У пользователя должна быть хотя бы одна роль")
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "person_security_role")
    @JoinColumn(referencedColumnName = "person_id")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}