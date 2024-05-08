package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.UserAssembler;
import com.gabrielgua.realworld.api.model.user.UserResponse;
import com.gabrielgua.realworld.api.model.user.UserUpdate;
import com.gabrielgua.realworld.api.security.authorization.CheckSecurity;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    @GetMapping
    @CheckSecurity.Protected.canManage
    public UserResponse getCurrentUser() {
        var user = userService.getCurrentUser();
        return userAssembler.toResponse(user);
    }

    @PutMapping
    @CheckSecurity.Protected.canManage
    public UserResponse updateCurrentUser(@RequestBody UserUpdate userUpdate) {
        var currentUser = userService.getCurrentUser();
        userAssembler.copyToEntity(userUpdate, currentUser);
        return userAssembler.toResponse(userService.save(currentUser, currentUser.getProfile()));
    }


}
