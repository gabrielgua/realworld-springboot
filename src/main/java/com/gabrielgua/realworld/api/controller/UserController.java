package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ArticleAssembler;
import com.gabrielgua.realworld.api.assembler.UserAssembler;
import com.gabrielgua.realworld.api.model.ArticleResponse;
import com.gabrielgua.realworld.api.model.UserResponse;
import com.gabrielgua.realworld.api.model.UserUpdate;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;



    @GetMapping
    public UserResponse getCurrentUser() {
        var user = userService.getCurrentUser();
        return userAssembler.toResponse(user);
    }

    @PutMapping
    public UserResponse updateCurrentUser(@RequestBody UserUpdate userUpdate) {
        var currentUser = userService.getCurrentUser();
        userAssembler.copyToEntity(userUpdate, currentUser);
        return userAssembler.toResponse(userService.save(currentUser));
    }


}
