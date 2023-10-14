package com.it.mentor.org.springbootsecuritydemo.services;

import com.it.mentor.org.springbootsecuritydemo.models.*;
import com.it.mentor.org.springbootsecuritydemo.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import javax.transaction.*;
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

        peopleRepository.save(person);
    }
}