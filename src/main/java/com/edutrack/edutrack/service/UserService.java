package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.CourseResponseDTO;
import com.edutrack.edutrack.dto.UserCreateDTO;
import com.edutrack.edutrack.dto.UserResponseDTO;
import com.edutrack.edutrack.dto.UserUpdateDTO;
import com.edutrack.edutrack.exception.UserAlreadyExistsException;
import com.edutrack.edutrack.exception.UserNotFoundException;
import com.edutrack.edutrack.mapper.CourseMapper;
import com.edutrack.edutrack.mapper.UserMapper;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;

    @PreAuthorize("hasAuthority('user:read:all')")
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toResponseDTO)
            .toList();
    }

    @PreAuthorize("hasAuthority('user:read:one')")
    public UserResponseDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDTO(user);
    }

    @PreAuthorize("hasAuthority('user:create')")
    public UserResponseDTO createUser(UserCreateDTO request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException(request.getEmail());
        });
        User user = userMapper.toEntity(request);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('user:update')")
    public UserResponseDTO updateUser(Long id, UserUpdateDTO request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.findByEmail(request.getEmail())
            .filter(existingUser -> !existingUser.getId().equals(id))
            .ifPresent(existingUser -> {
                throw new UserAlreadyExistsException(request.getEmail());
            });
        userMapper.updateEntity(request, user);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('user:delete')")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('course:read:all:by:user')")
    public List<CourseResponseDTO> getCoursesForUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getCourses().stream()
            .map(courseMapper::toResponseDTO)
            .toList();
    }
}
