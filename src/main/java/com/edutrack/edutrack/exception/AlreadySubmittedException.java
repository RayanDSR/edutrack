package com.edutrack.edutrack.exception;

public class AlreadySubmittedException extends EntityAlreadyExistsException {
    public AlreadySubmittedException(String message) {
        super(message);
    }
}
