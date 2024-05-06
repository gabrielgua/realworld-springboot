package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ProfileAssembler;
import com.gabrielgua.realworld.api.model.profile.ProfileResponse;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.api.security.authorization.CheckSecurity;
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
    @CheckSecurity.Public.canRead
    public ProfileResponse getByUsername(@PathVariable String username, WebRequest request) {
        if (authUtils.isAuthenticated()) {
            var currentUser = userService.getCurrentUser().getProfile();
            return profileAssembler.toResponse(currentUser, profileService.getByUsername(username));
        }

        return profileAssembler.toResponse(profileService.getByUsername(username));
    }

    @PostMapping("/{username}/follow")
    @CheckSecurity.Protected.canManage
    public ProfileResponse followProfile(@PathVariable String username) {
        var toFollow = profileService.getByUsername(username);
        var current = userService.getCurrentUser().getProfile();

        profileService.follow(current, toFollow);
        return profileAssembler.toResponse(current, toFollow);
    }

    @DeleteMapping("/{username}/follow")
    @CheckSecurity.Protected.canManage
    public ProfileResponse unfollowProfile(@PathVariable String username) {
        var toFollow = profileService.getByUsername(username);
        var current = userService.getCurrentUser().getProfile();

        profileService.unfollow(current, toFollow);
        return profileAssembler.toResponse(current, toFollow);
    }
}
