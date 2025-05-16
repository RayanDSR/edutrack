package com.edutrack.edutrack.dto;

import com.edutrack.edutrack.model.Role;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {

    private String name;

    @Email(message = "User email is not valid")
    private String email;

    private String password;

    private Role role;
}
