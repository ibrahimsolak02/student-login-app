package com.ibrahimsolak.studentloginapp.teacher;

import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher,Long> {
}
