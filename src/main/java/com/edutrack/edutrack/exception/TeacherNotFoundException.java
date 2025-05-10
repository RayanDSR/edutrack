package com.edutrack.edutrack.exception;

public class TeacherNotFoundException extends EntityNotFoundException {
    public TeacherNotFoundException(Long id) {
        super("Teacher with ID " + id + " not found.");
    }
}
