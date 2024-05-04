package com.gabrielgua.realworld.domain.exception;

public class AlreadyTakenException extends BusinessException {

    public AlreadyTakenException(String field) {
        super(String.format("%s has already been taken", field));
    }

}
