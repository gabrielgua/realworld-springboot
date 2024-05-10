package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.EmailNotFoundException;
import com.gabrielgua.realworld.domain.exception.EmailTakenException;
import com.gabrielgua.realworld.domain.exception.UsernameTakenException;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.repository.ProfileRepository;
import com.gabrielgua.realworld.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock private EntityManager entityManager;
    @Mock private ProfileService profileService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserRepository userRepository;
    @Mock private ProfileRepository profileRepository;


    @InjectMocks
    private UserService userService;

    private User user;
    private Profile profile;
    private User registerUser;
    private Profile registerProfile;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        profile = Profile.builder()
                .id(1L)
                .image("https://example.com/my-cool-image.png")
                .bio(null)
                .user(user)
                .username("test")
                .build();


        user = User.builder()
                .id(1L)
                .email("test@email.com")
                .token("Token ...")
                .profile(profile)
                .password("$2a$12$EChU9fyeHkFjurqzKDb3Z.z7GHRSV5OzVmQ./lnqWVF3ozEHQpQJS")
                .build();


        registerUser = User.builder()
                .email("test@email.com")
                .password("mystrongpassword")
                .token("")
                .build();

        registerProfile = Profile.builder()
                .username("test")
                .user(registerUser)
                .build();
    }

    @Test
    public void should_get_user_by_email_successfully() {
        var email = "test@email.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));

        var userOptional = userService.getByEmail(email);

        assertThat(userOptional).isNotNull();
        assertThat(userOptional.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    public void should_throw_EmailNotFoundException_when_email_not_found() {
        var email = "thisemail@doesnt.exist.com";
        when(userRepository.findByEmail(email)).thenThrow(EmailNotFoundException.class);

        assertThatExceptionOfType(EmailNotFoundException.class)
                .isThrownBy(() -> userService.getByEmail(email));
    }

    @Test
    public void should_save_a_new_user_successfully() {
        when(profileRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());


        when(userRepository.save(any(User.class))).thenReturn(user);
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);


        var savedUser = userService.save(registerUser, registerProfile);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getPassword()).isNotBlank();
        assertThat(savedUser.getToken()).startsWith("Token ");
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());

        assertThat(savedUser.getProfile().getBio()).isNull();
        assertThat(savedUser.getProfile().getId()).isEqualTo((user.getId()));
        assertThat(savedUser.getProfile().getUsername()).isEqualTo(profile.getUsername());
        assertThat(savedUser.getProfile().getImage()).isEqualTo(profile.getImage());
    }

    @Test
    public void should_throw_EmailTakenException_when_saving_user_with_duplicate_email() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(user));

        assertThatExceptionOfType(EmailTakenException.class)
                .isThrownBy(() -> userService.save(registerUser, registerProfile))
                .withMessage("has already been taken");
    }

    @Test
    public void should_throw_UsernameTakenException_when_saving_user_with_duplicate_username() {
        when(profileRepository.findByUsername("test")).thenReturn(Optional.ofNullable(profile));

        assertThatExceptionOfType(UsernameTakenException.class)
                .isThrownBy(() -> userService.save(registerUser, registerProfile))
                .withMessage("has already been taken");
    }
}