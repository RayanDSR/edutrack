package com.edutrack.edutrack.dto;

import com.edutrack.edutrack.model.Role;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
