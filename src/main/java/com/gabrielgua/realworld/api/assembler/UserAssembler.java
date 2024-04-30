package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.UserAuthenticate;
import com.gabrielgua.realworld.api.model.UserRegister;
import com.gabrielgua.realworld.api.model.UserResponse;
import com.gabrielgua.realworld.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse toResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User toEntity(UserRegister register) {
        return modelMapper.map(register, User.class);
    }
}
