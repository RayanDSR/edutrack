package com.edutrack.edutrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrack.edutrack.dto.CourseResponseDTO;
import com.edutrack.edutrack.dto.UserResponseDTO;
import com.edutrack.edutrack.dto.UserUpdateDTO;
import com.edutrack.edutrack.dto.UserCreateDTO;
import com.edutrack.edutrack.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponseDTO> getAllUses() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserCreateDTO newUser) {
        return userService.createUser(newUser);
    }

    @PatchMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/courses")
    public List<CourseResponseDTO> getCoursesByUser(@PathVariable Long id) {
        return userService.getCoursesForUser(id);
    }
}
