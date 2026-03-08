package com.ibrahimsolak.studentloginapp.grade.repository;

import com.ibrahimsolak.studentloginapp.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findFirstByStudentIdAndCourseIdOrderByIdDesc(Long studentId, Long courseId);
}
