package com.gabrielgua.realworld.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleResponse {

    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean favorited;
    private int favoritesCount;
    private ProfileResponse author;
}
