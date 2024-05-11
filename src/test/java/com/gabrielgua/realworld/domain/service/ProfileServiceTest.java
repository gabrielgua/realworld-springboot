package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.ProfileNotFoundException;
import com.gabrielgua.realworld.domain.exception.UsernameTakenException;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ProfileServiceTest {

    @Mock private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    private User user;
    private Profile profile;
    private Article article;
    private Profile profileFollower;
    private Profile profileHasFavorited;
    private final String DEFAULT_IMAGE_URL = "https://api.realworld.io/images/smiley-cyrus.jpeg";


    @BeforeEach
    void setUp() {
        openMocks(this);

        profile = Profile.builder()
                .id(1L)
                .image("https://example.com/my-cool-image.png")
                .bio(null)
                .user(user)
                .username("test")
                .build();


        profileFollower = Profile.builder()
                .id(2L)
                .image("https://example.com/my-cool-image.png")
                .bio("I'm a follower of profile")
                .username("follower")
                .profiles(new HashSet<>())
                .articles(new HashSet<>())
                .build();


        user = User.builder()
                .id(1L)
                .email("test@email.com")
                .token("Token ...")
                .profile(profile)
                .password("$2a$12$EChU9fyeHkFjurqzKDb3Z.z7GHRSV5OzVmQ./lnqWVF3ozEHQpQJS")
                .build();

        article = Article.builder()
                .title("How to test")
                .slug("how-to-test")
                .body("With JUnit5 and Mockito obviously")
                .description("You'll discover how to test")
                .build();
    }

    @Test
    public void should_get_profile_by_username_successfully() {
        when(profileRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(profile));

        var foundProfile = profileService.getByUsername("test");

        assertThat(foundProfile).isNotNull();
        assertThat(foundProfile.getId()).isEqualTo(profile.getId());
        assertThat(foundProfile.getBio()).isEqualTo(profile.getBio());
        assertThat(foundProfile.getUser()).isEqualTo(profile.getUser());
        assertThat(foundProfile.getImage()).isEqualTo(profile.getImage());
        assertThat(foundProfile.getUsername()).isEqualTo(profile.getUsername());
        assertThat(foundProfile.getProfiles()).isEqualTo(profile.getProfiles());
        assertThat(foundProfile.getArticles()).isEqualTo(profile.getArticles());

        verify(profileRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void should_throw_ProfileNotFoundException_when_username_not_found() {
        when(profileRepository.findByUsername(anyString())).thenThrow(ProfileNotFoundException.class);

        assertThatExceptionOfType(ProfileNotFoundException.class)
                .isThrownBy(() -> profileService.getByUsername(anyString()));
    }

    @Test
    public void should_save_a_profile_successfully() {
        var registerProfile = Profile.builder().username("test").build();
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        var savedProfile = profileService.save(registerProfile);

        assertThat(savedProfile.getId()).isEqualTo(profile.getId());
        assertThat(savedProfile.getBio()).isEqualTo(profile.getBio());
        assertThat(savedProfile.getUser()).isEqualTo(profile.getUser());
        assertThat(savedProfile.getImage()).isEqualTo(profile.getImage());
        assertThat(savedProfile.getUsername()).isEqualTo(profile.getUsername());
        assertThat(savedProfile.getProfiles()).isEqualTo(profile.getProfiles());
        assertThat(savedProfile.getArticles()).isEqualTo(profile.getArticles());
    }

    @Test
    public void should_create_new_profile_entity_successfully() {

        var username = "test";
        var created = profileService.createNewProfile(user, username);

        assertThat(created).isNotNull();
        assertThat(created.getBio()).isNull();
        assertThat(created.getProfiles()).isNull();
        assertThat(created.getArticles()).isNull();
        assertThat(created.getUser()).isEqualTo(user);
        assertThat(created.getUsername()).isEqualTo(username);
        assertThat(created.getImage()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    public void should_follow_profile_successfully() {
        profileService.follow(profileFollower, profile);
        assertThat(profileFollower.getProfiles().contains(profile)).isTrue();
        assertThat(profileFollower.getProfiles().size()).isOne();

        verify(profileRepository, times(1)).save(any(Profile.class));

    }

    @Test
    public void should_unfollow_profile_successfully() {
        profileService.follow(profileFollower, profile);

        profileService.unfollow(profileFollower, profile);
        assertThat(profileFollower.getProfiles().contains(profile)).isFalse();
        assertThat(profileFollower.getProfiles().size()).isZero();

        verify(profileRepository, times(2)).save(any(Profile.class));
    }

    @Test
    public void should_favorite_an_article_successfully() {
        profileService.favorite(profileFollower, article);

        assertThat(profileFollower.getArticles().contains(article)).isTrue();
        assertThat(profileFollower.getArticles().size()).isOne();

        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    @Test
    public void should_unfavorite_an_article_successfully() {
        profileService.favorite(profileFollower, article);

        profileService.unfavorite(profileFollower, article);

        assertThat(profileFollower.getArticles().contains(article)).isFalse();
        assertThat(profileFollower.getArticles().size()).isZero();

        verify(profileRepository, times(2)).save(any(Profile.class));
    }








}