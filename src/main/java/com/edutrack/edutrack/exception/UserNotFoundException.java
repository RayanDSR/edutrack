package com.edutrack.edutrack.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found.");
    }

    public UserNotFoundException(String email) {
        super("User with email " + email + " not found.");
    }
}
