package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.exception.TeacherNotFoundException;
import com.edutrack.edutrack.repository.TeacherRepository;
import com.edutrack.model.Teacher;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = getTeacher(id);
        teacher.setName(teacherDetails.getName());
        teacher.setEmail(teacherDetails.getEmail());
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
