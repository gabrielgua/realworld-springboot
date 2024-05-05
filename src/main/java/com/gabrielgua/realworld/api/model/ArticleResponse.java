package com.gabrielgua.realworld.api.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@JsonTypeName("article")
public class ArticleResponse extends BaseResponse {

    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean favorited;
    private int favoritesCount;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private ProfileResponse author;
}
