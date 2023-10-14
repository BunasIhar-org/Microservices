package com.it.mentor.org.firstrestapp.models;

import lombok.*;
import javax.persistence.*;
import java.time.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_rest")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String email;

    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "created_who")
    private String createWho;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "person_rest_role")
    @JoinColumn(referencedColumnName = "person_id")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}