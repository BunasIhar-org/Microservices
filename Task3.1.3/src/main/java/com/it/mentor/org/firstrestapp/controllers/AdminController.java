package com.it.mentor.org.firstrestapp.controllers;

import com.it.mentor.org.firstrestapp.dto.*;
import com.it.mentor.org.firstrestapp.models.*;
import com.it.mentor.org.firstrestapp.services.*;
import com.it.mentor.org.firstrestapp.util.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    private final ModelMapper modelMapper;

    private final PersonValidator personValidator;

    @Autowired
    public AdminController(AdminService adminService, ModelMapper modelMapper, PersonValidator personValidator) {
        this.adminService = adminService;
        this.modelMapper = modelMapper;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public List<Person> getPeople() {
        return adminService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") Integer id) {
        return adminService.findOne(id);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {
        Person person = modelMapper.map(personDTO, Person.class);

        personValidator.validate(person, bindingResult);
        personValidator.validateRequest(bindingResult);

        adminService.savePerson(person);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") Integer id,
                                                   @RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {
        Person person = modelMapper.map(personDTO, Person.class);

        personValidator.validate(person, bindingResult);
        personValidator.validateRequest(bindingResult);

        adminService.updatePerson(person, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") Integer id) {

        adminService.deletePerson(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
