package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ProfileAssembler;
import com.gabrielgua.realworld.api.model.ProfileResponse;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.api.security.CheckSecurity;
import com.gabrielgua.realworld.domain.service.ProfileService;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileAssembler profileAssembler;
    private final AuthUtils authUtils;

    @GetMapping("/{username}")
    @CheckSecurity.Default.canRead
    public ProfileResponse getByUsername(@PathVariable String username, WebRequest request) {
        if (request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
            return profileAssembler.toResponse(profileService.getByUsername(username));
        }

        var user = userService.getByEmail(authUtils.getCurrentUserEmail());
        return profileAssembler.toResponse(user, profileService.getByUsername(username));
    }

    @PostMapping("/{username}/follow")
    public ProfileResponse followProfile(@PathVariable String username) {
        var profile = profileService.getByUsername(username);
        var user = userService.getByEmail(authUtils.getCurrentUserEmail());

        userService.follow(user, profile);
        return profileAssembler.toResponse(user, profile);
    }

    @DeleteMapping("/{username}/follow")
    public ProfileResponse unfollowProfile(@PathVariable String username) {
        var profile = profileService.getByUsername(username);
        var user = userService.getByEmail(authUtils.getCurrentUserEmail());

        userService.unfollow(user, profile);
        return profileAssembler.toResponse(user, profile);
    }


}
