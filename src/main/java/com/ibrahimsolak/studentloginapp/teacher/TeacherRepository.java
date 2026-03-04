package com.ibrahimsolak.studentloginapp.teacher;

import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeacherRepository extends CrudRepository<Teacher,Long> {

    Optional<Teacher> findByUserId(Long userId);
}
