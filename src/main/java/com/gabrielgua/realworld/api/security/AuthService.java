package com.gabrielgua.realworld.api.security;

import com.gabrielgua.realworld.api.model.UserAuthenticate;
import com.gabrielgua.realworld.api.model.UserResponse;
import com.gabrielgua.realworld.api.model.UserToken;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserResponse register(User user) {
        var token = tokenService.generateToken(setDefaultClaims(user), user.getEmail());
        userService.setToken(user, token);
        return userToUserResponse(token, user);
    }

    public UserResponse authenticate(UserAuthenticate authenticate) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticate.getEmail(), authenticate.getPassword())
        );

        var user = userService.findByEmail(authenticate.getEmail());
        var token = tokenService.generateToken(setDefaultClaims(user), user.getEmail());

        userService.setToken(user, token);
        return userToUserResponse(token, user);
    }

    private UserResponse userToUserResponse(String token, User user) {
        return UserResponse
                .builder()
                .bio(user.getBio())
                .email(user.getEmail())
                .image(user.getImage())
                .token(token)
                .username(user.getUsername())
                .build();
    }

    private Map<String, Object> setDefaultClaims(User user) {
        var claims = new HashMap<String, Object>();
        var userToken = UserToken
                .builder()
                .id(user.getId())
                .build();

        claims.put("user", userToken);
        return claims;
    }
}
