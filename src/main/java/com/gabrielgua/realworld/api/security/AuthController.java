package com.gabrielgua.realworld.api.security;

import com.gabrielgua.realworld.api.assembler.UserAssembler;
import com.gabrielgua.realworld.api.model.user.UserAuthenticate;
import com.gabrielgua.realworld.api.model.user.UserRegister;
import com.gabrielgua.realworld.api.model.user.UserResponse;
import com.gabrielgua.realworld.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final UserAssembler userAssembler;

    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegister register) {
        var user = userAssembler.toEntity(register);
        return ResponseEntity.ok(authService.register(userService.save(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@Valid @RequestBody UserAuthenticate authenticate) {
        return ResponseEntity.ok(authService.authenticate(authenticate));
    }
}
