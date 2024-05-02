package com.gabrielgua.realworld.domain.exception;



public class TagNotFoundException extends ResourceNotFoundException{
    public TagNotFoundException(Long id) {
        super(String.format("Tag not found for id: %s", id));
    }
}


