package com.ibrahimsolak.studentloginapp.student.repository;

import com.ibrahimsolak.studentloginapp.student.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByUserId(Long id);

    List<Student> findByIdIn(List<Long> studentIds);
}
