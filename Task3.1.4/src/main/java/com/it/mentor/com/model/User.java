package com.it.mentor.com.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String name;

    private String lastName;

    private Byte age;
}