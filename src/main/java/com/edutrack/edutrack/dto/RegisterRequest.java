package com.edutrack.edutrack.dto;

import com.edutrack.edutrack.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "User name is required")
    private String name;

    @NotBlank(message = "User email is required")
    @Email(message = "User email is not valid")
    private String email;

    @NotBlank(message = "User password is required")
    private String password;

    @NotNull(message = "User role is required")
    private Role role;
}
