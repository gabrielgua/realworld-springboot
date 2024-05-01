package com.gabrielgua.realworld.domain.exception;

import java.util.Map;

public class AlreadyTakenException extends GenericException {

    public AlreadyTakenException(String field) {
        super(String.format("%s has already been taken", field));
    }

}
