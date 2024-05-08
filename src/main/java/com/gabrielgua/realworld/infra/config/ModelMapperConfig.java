package com.gabrielgua.realworld.infra.config;


import com.gabrielgua.realworld.api.model.profile.ProfileResponse;
import com.gabrielgua.realworld.api.model.user.UserUpdate;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.User;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());


        var updateUserTypeMap = modelMapper.createTypeMap(UserUpdate.class, User.class);
        updateUserTypeMap.<String>addMapping(UserUpdate::getBio, (destination, value) -> destination.getProfile().setBio(value));
        updateUserTypeMap.<String>addMapping(UserUpdate::getImage, (destination, value) -> destination.getProfile().setImage(value));
        updateUserTypeMap.<String>addMapping(UserUpdate::getUsername, (destination, value) -> destination.getProfile().setUsername(value));


        return modelMapper;
    }


}

