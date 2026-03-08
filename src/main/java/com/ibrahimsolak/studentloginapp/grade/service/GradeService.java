package com.ibrahimsolak.studentloginapp.grade.service;

import com.ibrahimsolak.studentloginapp.grade.dto.GradeDTO;
import com.ibrahimsolak.studentloginapp.grade.dto.GradeViewDTO;
import com.ibrahimsolak.studentloginapp.grade.entity.Grade;

import java.util.List;

public interface GradeService {

    void submitGrade(GradeDTO gradeDTO);

    Grade getGradeByStudentIdAndCourse(Long studentId, Long courseId);

    List<GradeViewDTO> getMyGrades();
}
