package com.edutrack.edutrack.model;

public enum Permission {
    USER_CREATE("user:create"),
    USER_READ_ALL("user:read:all"),
    USER_READ_ONE("user:read:one"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    USER_READ_ALL_BY_COURSE("user:read:all:by:course"),

    COURSE_CREATE("course:create"),
    COURSE_READ_ALL("course:read:all"),
    COURSE_READ_ONE("course:read:one"),
    COURSE_UPDATE("course:update"),
    COURSE_DELETE("course:delete"),
    COURSE_READ_ALL_BY_USER("course:read:all:by:user"),

    COURSE_ENROLL("course:enroll"),
    ENROLLMENT_READ_OWN("enrollment:read:own"),

    ASSIGNMENT_CREATE("assignment:create"),
    ASSIGNMENT_READ_ALL_BY_COURSE("assignment:read:all:by:course"),

    SUBMISSION_CREATE("submission:create"),
    SUBMISSION_READ_ALL_BY_ASSIGNMENT("submission:read:all:by:assignment"),
    SUBMISSION_GRADE("submission:grade");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
