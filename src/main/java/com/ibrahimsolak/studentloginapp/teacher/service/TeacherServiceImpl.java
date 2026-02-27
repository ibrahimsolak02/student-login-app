package com.ibrahimsolak.studentloginapp.teacher.service;

import com.ibrahimsolak.studentloginapp.exception.EntityNotFoundException;
import com.ibrahimsolak.studentloginapp.teacher.TeacherRepository;
import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;

    @Override
    public Teacher getTeacherById(Long id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        return unWrap(teacherOptional, id);
    }

    static Teacher unWrap(Optional<Teacher> teacher, Long id) {
        if(teacher.isPresent()) {
            return teacher.get();
        } else {
            throw new EntityNotFoundException(id, Teacher.class);
        }
    }
}
