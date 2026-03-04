package com.ibrahimsolak.studentloginapp.teacher.service;

import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;

public interface TeacherService {
    Teacher getTeacherById(Long id);

    Teacher getTeacherByUserId(Long userId);
}
