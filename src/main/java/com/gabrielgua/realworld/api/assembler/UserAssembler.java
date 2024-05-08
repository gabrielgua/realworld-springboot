package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.user.UserRegister;
import com.gabrielgua.realworld.api.model.user.UserResponse;
import com.gabrielgua.realworld.api.model.user.UserUpdate;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAssembler {

    private final ModelMapper modelMapper;

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .token(user.getToken())
                .bio(user.getProfile().getBio())
                .image(user.getProfile().getImage())
                .username(user.getProfile().getUsername())
                .build();
    }

    public User toEntity(UserRegister register) {
        return modelMapper.map(register, User.class);
    }

    public void copyToEntity(UserUpdate update, User user) {
        modelMapper.map(update, user);
    }
}
