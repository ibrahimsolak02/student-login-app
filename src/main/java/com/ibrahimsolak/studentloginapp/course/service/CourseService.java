package com.ibrahimsolak.studentloginapp.course.service;

import com.ibrahimsolak.studentloginapp.course.entity.Course;

public interface CourseService {

    Course saveCourse(Course course);

    void deleteCourse(Long id);
}
