package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.profile.ProfileResponse;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileAssembler {
    private final ModelMapper modelMapper;

    public ProfileResponse toResponse(Profile profile) {
        return modelMapper.map(profile, ProfileResponse.class);
    }

    public ProfileResponse toResponse(Profile current, Profile profile) {
        var response = toResponse(profile);

        var isFollowing = current.getProfiles().contains(profile);
        response.setFollowing(isFollowing);

        return response;
    }
}
