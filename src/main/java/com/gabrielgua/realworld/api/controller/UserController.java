package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ProfileAssembler;
import com.gabrielgua.realworld.api.assembler.UserAssembler;
import com.gabrielgua.realworld.api.model.ProfileResponse;
import com.gabrielgua.realworld.api.model.UserResponse;
import com.gabrielgua.realworld.api.model.UserUpdate;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.service.ProfileService;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;
    private final AuthUtils authUtils;


    @GetMapping
    public UserResponse getCurrentUser() {
        var user = userService.findByEmail(authUtils.getCurrentUserEmail());
        return userAssembler.toResponse(user);
    }

    @PutMapping
    public UserResponse updateCurrentUser(@RequestBody UserUpdate userUpdate) {
        var currentUser = userService.findByEmail(authUtils.getCurrentUserEmail());
        userAssembler.copyToEntity(userUpdate, currentUser);
        return userAssembler.toResponse(userService.save(currentUser));
    }
}
