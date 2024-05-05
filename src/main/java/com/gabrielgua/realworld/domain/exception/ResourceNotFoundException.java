package com.gabrielgua.realworld.domain.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException() {
        super("not found");
    }
}
