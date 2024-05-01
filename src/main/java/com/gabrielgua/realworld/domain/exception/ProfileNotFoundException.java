package com.gabrielgua.realworld.domain.exception;

public class ProfileNotFoundException extends ResourceNotFoundException {
    public ProfileNotFoundException(String username) {
        super(String.format("No profile found for username: '%s'", username));
    }

    public ProfileNotFoundException(Long profileId) {
        super(String.format("No profile found for id: '%s'", profileId));
    }


}
