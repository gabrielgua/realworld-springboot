package com.gabrielgua.realworld.api.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("profile")
public class ProfileResponse extends BaseResponse {

    private String username;
    private String bio;
    private String image;
    private boolean following;
}
