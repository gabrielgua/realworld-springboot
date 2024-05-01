package com.gabrielgua.realworld.domain.exception;

public class EmailNotFoundException extends ResourceNotFoundException {

    public EmailNotFoundException(String email) {
        super(String.format("No user found for email: '%s'", email));
    }
}
