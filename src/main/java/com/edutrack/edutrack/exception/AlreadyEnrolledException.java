package com.edutrack.edutrack.exception;

public class AlreadyEnrolledException extends EntityAlreadyExistsException {
    public AlreadyEnrolledException(String message) {
        super(message);
    }
}
