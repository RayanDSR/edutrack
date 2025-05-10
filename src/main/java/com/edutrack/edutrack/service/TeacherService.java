package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.dto.CourseDTO;
import com.edutrack.edutrack.dto.TeacherDTO;
import com.edutrack.edutrack.dto.TeacherRequestDTO;
import com.edutrack.edutrack.exception.TeacherNotFoundException;
import com.edutrack.edutrack.model.Teacher;
import com.edutrack.edutrack.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
            .map(TeacherDTO::new)
            .toList();
    }

    public TeacherDTO getTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));
        return new TeacherDTO(teacher);
    }

    public TeacherDTO createTeacher(TeacherRequestDTO request) {
        Teacher teacher = Teacher.builder()
            .name(request.getName())
            .email(request.getEmail())
            .build();

        return new TeacherDTO(teacherRepository.save(teacher));
    }

    public TeacherDTO updateTeacher(Long id, TeacherRequestDTO request) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));

        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());

        return new TeacherDTO(teacherRepository.save(teacher));
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public List<CourseDTO> getCoursesForTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));
        return teacher.getCourses().stream()
            .map(CourseDTO::new)
            .toList();
    }
}
