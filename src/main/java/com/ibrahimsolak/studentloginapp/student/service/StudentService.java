package com.ibrahimsolak.studentloginapp.student.service;

import com.ibrahimsolak.studentloginapp.student.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    Student getStudentById(Long id);

    Student getStudentByUserId(Long userId);

    Map<Long, Student> getStudentsByIdIn(List<Long> idList);
}
