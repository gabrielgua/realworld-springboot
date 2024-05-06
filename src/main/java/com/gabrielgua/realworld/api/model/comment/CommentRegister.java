package com.gabrielgua.realworld.api.model.comment;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gabrielgua.realworld.api.model.BaseResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("comment")
public class CommentRegister extends BaseResponse {

    @NotBlank
    private String body;
}
