package com.it.mentor.org.firstrestapp.controllers;

import com.it.mentor.org.firstrestapp.dto.*;
import com.it.mentor.org.firstrestapp.models.*;
import com.it.mentor.org.firstrestapp.security.*;
import com.it.mentor.org.firstrestapp.services.*;
import com.it.mentor.org.firstrestapp.util.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;

    private final RegistrationService registrationService;

    private final JWTUtil jwtUtil;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService,
                          JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {

        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(), authenticationDTO.getPassword()
        );
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        }
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());

        return Map.of("jwt-token", token);
    }

    @PostMapping("/registration")
    public Map<String, String> registration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {

        Person person = modelMapper.map(personDTO, Person.class);

        personValidator.validate(person, bindingResult);
        personValidator.validateRequest(bindingResult);

        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getUsername());

        return Map.of("jwt-token", token);
    }
}