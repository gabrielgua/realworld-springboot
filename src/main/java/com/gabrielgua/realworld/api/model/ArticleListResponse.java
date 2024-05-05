package com.gabrielgua.realworld.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ArticleListResponse {

    private List<ArticleResponse> articles;
    private int articlesCount;
}
