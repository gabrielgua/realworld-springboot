package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.profile.ProfileResponse;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileAssembler {

    private final AuthUtils authUtils;
    private final UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public ProfileResponse toResponse(Profile profile) {
        return modelMapper.map(profile, ProfileResponse.class);
    }

    public ProfileResponse toResponse(User user, Profile profile) {
        var response = modelMapper.map(profile, ProfileResponse.class);

        if (user.getFollowing().contains(profile)) {
            response.setFollowing(true);
        }
        return response;
    }
}
