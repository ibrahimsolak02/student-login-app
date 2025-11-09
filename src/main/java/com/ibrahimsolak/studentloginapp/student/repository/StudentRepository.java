package com.ibrahimsolak.studentloginapp.student.repository;

import com.ibrahimsolak.studentloginapp.student.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
