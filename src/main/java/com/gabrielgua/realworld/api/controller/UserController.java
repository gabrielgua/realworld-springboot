package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.UserAssembler;
import com.gabrielgua.realworld.api.model.UserResponse;
import com.gabrielgua.realworld.api.model.UserUpdate;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
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
        var user = userService.getByEmail(authUtils.getCurrentUserEmail());
        return userAssembler.toResponse(user);
    }

    @PutMapping
    public UserResponse updateCurrentUser(@RequestBody UserUpdate userUpdate) {
        var currentUser = userService.getByEmail(authUtils.getCurrentUserEmail());
        userAssembler.copyToEntity(userUpdate, currentUser);
        return userAssembler.toResponse(userService.save(currentUser));
    }
}
