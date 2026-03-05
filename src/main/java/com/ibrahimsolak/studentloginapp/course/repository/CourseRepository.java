package com.ibrahimsolak.studentloginapp.course.repository;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    List<Course> findByStudentsId(Long studentsId);
}
