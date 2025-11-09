package com.ibrahimsolak.studentloginapp.grade.service;

import com.ibrahimsolak.studentloginapp.grade.entity.Grade;

public interface GradeService {

    Grade submitGrade(Grade grade, Long courseId, Long studentId);
}
