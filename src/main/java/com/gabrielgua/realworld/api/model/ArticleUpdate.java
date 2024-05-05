package com.gabrielgua.realworld.api.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("article")
public class ArticleUpdate extends BaseResponse {

    @Nullable
    private String title;

    @Nullable
    private String description;

    @Nullable
    private String body;
}
