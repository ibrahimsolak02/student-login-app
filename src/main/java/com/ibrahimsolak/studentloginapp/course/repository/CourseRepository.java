package com.ibrahimsolak.studentloginapp.course.repository;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.projection.CourseEnrollmentView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    List<Course> findByStudentsId(Long studentId);

    List<Course> findByTeacherId(Long teacherId);

    @Query(value = "SELECT course_id as courseId, student_id as studentId FROM course_enrollment WHERE course_id IN :courseIds", nativeQuery = true)
    List<CourseEnrollmentView> findEnrollmentViewsByCourseIds(@Param("courseIds") List<Long> courseIds);
}
