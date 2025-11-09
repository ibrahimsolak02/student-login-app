package com.ibrahimsolak.studentloginapp.grade.service;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.service.CourseService;
import com.ibrahimsolak.studentloginapp.exception.StudentNotEnrolledException;
import com.ibrahimsolak.studentloginapp.grade.entity.Grade;
import com.ibrahimsolak.studentloginapp.grade.repository.GradeRepository;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.student.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    @Override
    public Grade submitGrade(Grade grade, Long courseId, Long studentId) {
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);
        if(!student.getCourses().contains(course)) {
            throw new StudentNotEnrolledException(studentId, courseId);
        }
        grade.setStudent(student);
        grade.setCourse(course);
        return gradeRepository.save(grade);
    }
}
