package com.ibrahimsolak.studentloginapp.course.service;

import com.ibrahimsolak.studentloginapp.course.entity.Course;

import java.util.List;

public interface CourseService {

    Course saveCourse(Course course);

    void deleteCourse(Long id);

    Course addStudentToCourse(Long courseId, Long studentId);

    Course getCourseById(Long id);

    List<Course> getAllCourses();
}
