package com.ibrahimsolak.studentloginapp.course.repository;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
