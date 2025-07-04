package com.edutrack.edutrack.dto;

import com.edutrack.edutrack.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String name;

    private String email;

    private Role role;
}
