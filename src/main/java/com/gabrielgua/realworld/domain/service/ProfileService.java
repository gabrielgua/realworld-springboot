package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;

    @Transactional(readOnly = true)
    public Profile getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void save(User user) {
        var profile = repository.findById(user.getId());

        if (profile.isEmpty()) {
            var newProfile = Profile
                    .builder()
                    .user(user)
                    .username(user.getUsername())
                    .bio(user.getBio())
                    .image(user.getImage())
                    .build();

            repository.save(newProfile);
            return;
        }

        var existingProfile = profile.get();
        existingProfile.setBio(user.getBio());
        existingProfile.setUsername(user.getUsername());
        existingProfile.setImage(user.getImage());

        repository.save(existingProfile);
    }
}
