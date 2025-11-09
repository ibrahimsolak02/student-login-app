package com.ibrahimsolak.studentloginapp.student.service;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.repository.CourseRepository;
import com.ibrahimsolak.studentloginapp.exception.EntityNotFoundException;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return unWrapStudent(student,id);
    }

    static Student unWrapStudent(Optional<Student> student, Long id) {
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new EntityNotFoundException(id, Student.class);
        }
    }
}
