package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.ProfileNotFoundException;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.repository.ProfileRepository;
import com.gabrielgua.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final String DEFAULT_IMAGE_URL = "https://api.realworld.io/images/smiley-cyrus.jpeg";


    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Profile getByUsername(String username) {
        return profileRepository.findByUsername(username).orElseThrow(ProfileNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Profile getById(Long id) {
        return profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
    }

    @Transactional
    public void save(Profile profile) {

        profile.setBio(profile.getBio());
        profile.setUsername(profile.getUsername());
        profile.setImage(profile.getImage());

        profileRepository.save(profile);
    }

    public Profile createNewProfile(User user, String username) {
        return Profile
                .builder()
                .user(user)
                .username(username)
                .bio(null)
                .image(DEFAULT_IMAGE_URL)
                .build();
    }

    public Profile updateProfile() {
        return new Profile();
    }

    @Transactional
    public void follow(Profile current, Profile toFollow) {
        current.followProfile(toFollow);
        profileRepository.save(current);
    }

    @Transactional
    public void unfollow(Profile current, Profile toFollow) {
        current.unfollowProfile(toFollow);
        profileRepository.save(current);
    }

    @Transactional
    public Profile favorite(Profile profile, Article article) {
        profile.favoriteArticle(article);
        return profileRepository.save(profile);
    }

    @Transactional
    public Profile unfavorite(Profile profile, Article article) {
        profile.unfavoriteArticle(article);
        return profileRepository.save(profile);
    }
}
