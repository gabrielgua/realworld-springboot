package com.gabrielgua.realworld.api.model.comment;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gabrielgua.realworld.api.model.BaseResponse;
import com.gabrielgua.realworld.api.model.profile.ProfileResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@JsonTypeName("comment")
public class CommentResponse extends BaseResponse {

    private Long id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String body;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private ProfileResponse author;
}
