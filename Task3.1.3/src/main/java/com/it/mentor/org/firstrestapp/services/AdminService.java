package com.it.mentor.org.firstrestapp.services;

import com.it.mentor.org.firstrestapp.models.*;
import com.it.mentor.org.firstrestapp.repositories.*;
import com.it.mentor.org.firstrestapp.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import java.time.*;
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

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(Integer id) {
        return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public void savePerson(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRoles(Collections.singleton(Role.ROLE_USER));
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdateAt(LocalDateTime.now());
        person.setCreateWho("ROLE_ADMIN");
        peopleRepository.save(person);
    }

    public void updatePerson(Person updatePerson, Integer id) {
        Person person = findOne(id);
        person.setUsername(updatePerson.getUsername());
        person.setEmail(updatePerson.getEmail());
        person.setPassword(passwordEncoder.encode(updatePerson.getPassword()));
        person.setUpdateAt(LocalDateTime.now());
        peopleRepository.save(person);
    }

    public void deletePerson(Integer id) {
        Person person = findOne(id);
        peopleRepository.delete(person);
    }
}
