package com.edutrack.edutrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrack.edutrack.model.Course;
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
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacher(id);
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher newTeacher) {
        return teacherService.createTeacher(newTeacher);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        return teacherService.updateTeacher(id, updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/courses")
    public List<Course> getCoursesByTeacher(@PathVariable Long id) {
        return teacherService.getCoursesForTeacher(id);
    }
}
