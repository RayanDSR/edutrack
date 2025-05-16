package com.edutrack.edutrack.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.edutrack.edutrack.dto.UserCreateDTO;
import com.edutrack.edutrack.dto.UserResponseDTO;
import com.edutrack.edutrack.dto.UserUpdateDTO;
import com.edutrack.edutrack.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public User toEntity(UserCreateDTO userCreateDTO) {
        return User.builder()
                .name(userCreateDTO.getName())
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword())
                .role(userCreateDTO.getRole())
                .build();
    }

    public void updateEntity(UserUpdateDTO userUpdateDTO, User user) {
        if (userUpdateDTO.getName() != null && !userUpdateDTO.getName().isEmpty()) {
            user.setName(userUpdateDTO.getName());
        }

        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().isEmpty()) {
            user.setEmail(userUpdateDTO.getEmail());
        }

        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }

        if (userUpdateDTO.getRole() != null) {
            user.setRole(userUpdateDTO.getRole());
        }
    }
}
