package com.gabrielgua.realworld.api.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gabrielgua.realworld.api.model.BaseResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonTypeName("user")
public class UserResponse extends BaseResponse {

    private String email;

    private String username;
    private String bio;
    private String image;

    private String token;
}
