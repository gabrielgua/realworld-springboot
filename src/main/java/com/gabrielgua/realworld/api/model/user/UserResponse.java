package com.gabrielgua.realworld.api.model.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gabrielgua.realworld.api.model.BaseResponse;
import com.gabrielgua.realworld.domain.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("user")
public class UserResponse extends BaseResponse {

    private String email;
    private String username;
    private String bio;
    private String image;
    private String token;
}
