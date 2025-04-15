package com.edutrack.edutrack.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrack.edutrack.dto.CourseDTO;
import com.edutrack.edutrack.dto.TeacherDTO;
import com.edutrack.edutrack.model.Teacher;
import com.edutrack.edutrack.service.TeacherService;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.getAllTeachers().stream()
            .map(TeacherDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TeacherDTO getTeacherById(@PathVariable Long id) {
        return new TeacherDTO(teacherService.getTeacher(id));
    }

    @PostMapping
    public TeacherDTO createTeacher(@RequestBody Teacher newTeacher) {
        return new TeacherDTO(teacherService.createTeacher(newTeacher));
    }

    @PutMapping("/{id}")
    public TeacherDTO updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        return new TeacherDTO(teacherService.updateTeacher(id, updatedTeacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/courses")
    public List<CourseDTO> getCoursesByTeacher(@PathVariable Long id) {
        return teacherService.getCoursesForTeacher(id).stream()
            .map(CourseDTO::new)
            .collect(Collectors.toList());
    }
}
