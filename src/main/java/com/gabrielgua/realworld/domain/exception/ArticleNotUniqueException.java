package com.gabrielgua.realworld.domain.exception;

public class ArticleNotUniqueException extends BusinessException {

    public ArticleNotUniqueException() {
        super("There's already an article with this title");
    }
}
