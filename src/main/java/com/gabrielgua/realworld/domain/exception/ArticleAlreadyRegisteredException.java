package com.gabrielgua.realworld.domain.exception;

public class ArticleAlreadyRegisteredException extends BusinessException {
    public ArticleAlreadyRegisteredException(String title) {
        super(String.format("There's already an article with this title: %s", title));
    }
}
