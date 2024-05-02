package com.gabrielgua.realworld.domain.exception;

public class ArticleNotFoundException extends ResourceNotFoundException {
    public ArticleNotFoundException(String slug) {
        super(String.format("Article not found for slug: %s", slug));
    }
}
