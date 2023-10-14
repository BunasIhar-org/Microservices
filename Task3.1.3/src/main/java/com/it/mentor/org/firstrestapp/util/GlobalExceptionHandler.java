package com.it.mentor.org.firstrestapp.util;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {

        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                e.getMessage(), LocalDateTime.now()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException ignoredE) {

        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person with this id wasn't found", LocalDateTime.now()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }
}