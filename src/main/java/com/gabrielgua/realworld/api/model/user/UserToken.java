package com.gabrielgua.realworld.api.model.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserToken {
    private Long id;
}
