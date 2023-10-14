package com.it.mentor.org.springbootsecuritydemo.models;

import org.springframework.security.core.*;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}