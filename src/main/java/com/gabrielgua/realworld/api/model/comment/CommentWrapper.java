package com.gabrielgua.realworld.api.model.comment;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CommentWrapper {

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    private List<CommentResponse> comments;
}
