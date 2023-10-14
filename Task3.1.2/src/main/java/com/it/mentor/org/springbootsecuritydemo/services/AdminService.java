package com.it.mentor.org.springbootsecuritydemo.services;

import com.it.mentor.org.springbootsecuritydemo.models.*;
import com.it.mentor.org.springbootsecuritydemo.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import java.util.*;

@Service
@Transactional
public class AdminService {

    private final PeopleRepository peopleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Person> findAllPeople() {
        return peopleRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Person findPersonById(Integer id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updatePerson(Person updatePerson) {
        Person person = findPersonById(updatePerson.getId());
        person.setUsername(updatePerson.getUsername());
        person.setEmail(updatePerson.getEmail());
        person.setPassword(passwordEncoder.encode(updatePerson.getPassword()));
        person.setRoles(updatePerson.getRoles());
        peopleRepository.save(person);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePerson(Integer id) {
        peopleRepository.deleteById(id);
    }
}