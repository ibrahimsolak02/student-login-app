package com.ibrahimsolak.studentloginapp.course.service;

import com.ibrahimsolak.studentloginapp.course.dto.CourseDTO;
import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.grade.dto.GradeDTO;

import java.util.List;

public interface CourseService {

    Course saveCourse(Course course);

    void deleteCourse(Long id);

    Course addStudentToCourse(Long courseId);

    Course getCourseById(Long id);

    List<CourseDTO> getAllCourses();

    List<CourseDTO> getEnrolledCourses();

    List<GradeDTO> getAllEnrollments();
}
