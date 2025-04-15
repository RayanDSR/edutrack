package com.edutrack.edutrack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edutrack.edutrack.exception.TeacherNotFoundException;
import com.edutrack.edutrack.model.Course;
import com.edutrack.edutrack.model.Teacher;
import com.edutrack.edutrack.repository.TeacherRepository;

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

        if (teacherDetails.getName() != null) {
            teacher.setName(teacherDetails.getName());
        }

        if (teacherDetails.getEmail() != null) {
            teacher.setEmail(teacherDetails.getEmail());
        }

        if (teacherDetails.getCourses() != null) {
            teacher.setCourses(teacherDetails.getCourses());
        }

        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public List<Course> getCoursesForTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException(teacherId));
        return teacher.getCourses();
    }
}
