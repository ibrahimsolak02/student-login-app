package com.ibrahimsolak.studentloginapp.student.service;

import com.ibrahimsolak.studentloginapp.exception.EntityNotFoundException;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return unWrapStudent(student,id);
    }

    @Override
    public Student getStudentByUserId(Long userId) {
        Optional<Student> student = studentRepository.findByUserId(userId);
        return unWrapStudent(student, userId );
    }

    @Override
    public Map<Long, Student> getStudentsByIdIn(List<Long> idList) {
        List<Student> students = studentRepository.findByIdIn(idList);
        return students.stream()
                .collect(Collectors.toMap(Student::getId, s -> s));
    }

    static Student unWrapStudent(Optional<Student> student, Long id) {
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new EntityNotFoundException(id, Student.class);
        }
    }
}
