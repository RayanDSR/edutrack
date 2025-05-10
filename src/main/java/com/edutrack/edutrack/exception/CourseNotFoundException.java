package com.edutrack.edutrack.exception;

public class CourseNotFoundException extends EntityNotFoundException {
    public CourseNotFoundException(Long id) {
        super("Course with ID " + id + " not found.");
    }
}
