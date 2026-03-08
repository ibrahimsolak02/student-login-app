package com.ibrahimsolak.studentloginapp.grade.service;

import com.ibrahimsolak.studentloginapp.grade.dto.GradeDTO;
import com.ibrahimsolak.studentloginapp.grade.entity.Grade;

public interface GradeService {

    void submitGrade(GradeDTO gradeDTO);

    Grade getGradeByStudentIdAndCourse(Long studentId, Long courseId);
}
