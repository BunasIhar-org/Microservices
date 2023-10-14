package com.it.mentor.org.springbootsecuritydemo.controllers;

import com.it.mentor.org.springbootsecuritydemo.security.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/person")
public class PersonController {

    @GetMapping("/info")
    public String hello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());
        return "person/info";
    }
}