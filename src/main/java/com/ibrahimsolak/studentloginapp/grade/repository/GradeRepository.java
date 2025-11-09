package com.ibrahimsolak.studentloginapp.grade.repository;

import com.ibrahimsolak.studentloginapp.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
