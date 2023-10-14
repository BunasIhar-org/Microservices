package com.it.mentor.org.firstrestapp.controllers;

import com.it.mentor.org.firstrestapp.models.*;
import com.it.mentor.org.firstrestapp.security.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @GetMapping()
    public Person showPersonInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return personDetails.getPerson();
    }
}