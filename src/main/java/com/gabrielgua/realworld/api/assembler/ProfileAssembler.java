package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.ProfileResponse;
import com.gabrielgua.realworld.domain.model.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProfileResponse toResponse(Profile profile) {
        return modelMapper.map(profile, ProfileResponse.class);
    }
}
