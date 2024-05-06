package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.user.UserRegister;
import com.gabrielgua.realworld.api.model.user.UserResponse;
import com.gabrielgua.realworld.api.model.user.UserUpdate;
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
        return modelMapper.map(user, UserResponse.class);
    }

    public User toEntity(UserRegister register) {
        return modelMapper.map(register, User.class);
    }

    public void copyToEntity(UserUpdate update, User user) {
        modelMapper.map(update, user);
    }

    public List<UserResponse> toCollectionResponse(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .toList();
    }
}
