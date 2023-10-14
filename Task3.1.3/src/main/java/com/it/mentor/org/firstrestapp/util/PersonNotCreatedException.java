package com.it.mentor.org.firstrestapp.util;

public class PersonNotCreatedException extends RuntimeException {

    public PersonNotCreatedException(String msg) {
        super(msg);
    }
}