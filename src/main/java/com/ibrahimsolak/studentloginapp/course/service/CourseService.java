package com.ibrahimsolak.studentloginapp.course.service;

import com.ibrahimsolak.studentloginapp.course.dto.CourseDTO;
import com.ibrahimsolak.studentloginapp.course.entity.Course;

import java.util.List;

public interface CourseService {

    Course saveCourse(Course course, Long teacherId);

    void deleteCourse(Long id);

    Course addStudentToCourse(Long courseId);

    Course getCourseById(Long id);

    List<CourseDTO> getAllCourses();
}
