package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.CourseResponseDTO;
import com.edutrack.edutrack.dto.UserCreateDTO;
import com.edutrack.edutrack.dto.UserResponseDTO;
import com.edutrack.edutrack.dto.UserUpdateDTO;
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

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toResponseDTO)
            .toList();
    }

    public UserResponseDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO createUser(UserCreateDTO request) {
        User user = userMapper.toEntity(request);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUser(Long id, UserUpdateDTO request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userMapper.updateEntity(request, user);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<CourseResponseDTO> getCoursesForUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getCourses().stream()
            .map(courseMapper::toResponseDTO)
            .toList();
    }
}
