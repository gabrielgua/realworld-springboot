package com.gabrielgua.realworld.domain.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleFilter {

    private String tag;
    private String author;
    private String favorited;
}
