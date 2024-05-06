package com.gabrielgua.realworld.api.model.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gabrielgua.realworld.api.model.BaseResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("user")
public class UserUpdate extends BaseResponse {

    @Email
    @Nullable
    private String email;

    @Nullable
    private String bio;

    @Nullable
    private String username;

    @Nullable
    private String password;

    @Nullable
    private String image;
}
