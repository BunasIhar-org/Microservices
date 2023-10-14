package com.it.mentor.org.firstrestapp.services;

import com.it.mentor.org.firstrestapp.models.*;
import com.it.mentor.org.firstrestapp.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import javax.transaction.*;
import java.time.*;
import java.util.*;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRoles(Collections.singleton(Role.ROLE_USER));
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdateAt(LocalDateTime.now());
        person.setCreateWho("ROLE_ADMIN");
        peopleRepository.save(person);
    }
}