package com.edutrack.edutrack.model;

import static com.edutrack.edutrack.model.Permission.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    STUDENT(
        Set.of(
            COURSE_READ_ALL,
            COURSE_READ_ONE,
            COURSE_ENROLL,
            ENROLLMENT_READ_OWN,
            SUBMISSION_CREATE,
            SUBMISSION_READ_ALL_BY_ASSIGNMENT
        )
    ),
    TEACHER(
        Set.of(
            COURSE_CREATE,
            COURSE_READ_ALL,
            COURSE_READ_ONE,
            COURSE_UPDATE,
            COURSE_DELETE,
            ASSIGNMENT_CREATE,
            ASSIGNMENT_READ_ALL_BY_COURSE,
            SUBMISSION_READ_ALL_BY_ASSIGNMENT,
            SUBMISSION_GRADE
        )
    ),
    ADMIN(
        Set.of(
            USER_CREATE,
            USER_READ_ALL,
            USER_READ_ONE,
            USER_UPDATE,
            USER_DELETE,
            USER_READ_ALL_BY_COURSE,
            COURSE_CREATE,
            COURSE_READ_ALL,
            COURSE_READ_ONE,
            COURSE_UPDATE,
            COURSE_DELETE,
            COURSE_READ_ALL_BY_USER,
            COURSE_ENROLL,
            ENROLLMENT_READ_OWN,
            ASSIGNMENT_CREATE,
            ASSIGNMENT_READ_ALL_BY_COURSE,
            SUBMISSION_CREATE,
            SUBMISSION_READ_ALL_BY_ASSIGNMENT,
            SUBMISSION_GRADE
        )
    );

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    // This method will generate the GrantedAuthority list for Spring Security
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
        // Add the role itself as an authority, prefixed with ROLE_ for compatibility with hasRole()
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
