package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.UserAuthenticate;
import com.gabrielgua.realworld.api.model.UserRegister;
import com.gabrielgua.realworld.api.model.UserResponse;
import com.gabrielgua.realworld.api.model.UserUpdate;
import com.gabrielgua.realworld.domain.model.Profile;
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

    public void copyToEntity(UserUpdate update, User user) {
        if (update.getBio() == null) {
            update.setBio(user.getBio());
        }

        if (update.getImage() == null) {
            update.setImage(user.getImage());
        }

        if (update.getEmail() == null) {
            update.setEmail(user.getEmail());
        }

        if (update.getUsername() == null) {
            update.setUsername(user.getUsername());
        }

        if (update.getPassword() == null) {
            update.setPassword(user.getPassword());
        }

        modelMapper.map(update, user);
    }
}
