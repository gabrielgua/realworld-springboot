package com.gabrielgua.realworld.domain.exception;

public class TakenException extends BusinessException {
    public TakenException() {
        super("has already been taken");
    }
}
