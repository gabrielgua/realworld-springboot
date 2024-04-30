package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ProfileService profileService;

    private final String DEFAULT_IMAGE_URL = "https://api.realworld.io/images/smiley-cyrus.jpeg";

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public User save(User user) {
        if (user.getId() == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setImage(DEFAULT_IMAGE_URL);
        }


        var savedUser = repository.save(user);
        profileService.save(user);
        return savedUser;

    }

    @Transactional
    public void setToken(User user, String token) {
        user.setToken(token);
        repository.save(user);
    }



}
