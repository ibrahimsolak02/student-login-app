package com.ibrahimsolak.studentloginapp.grade.service;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.service.CourseService;
import com.ibrahimsolak.studentloginapp.exception.StudentNotEnrolledException;
import com.ibrahimsolak.studentloginapp.exception.TeacherNotAssignedToCourseException;
import com.ibrahimsolak.studentloginapp.grade.entity.Grade;
import com.ibrahimsolak.studentloginapp.grade.repository.GradeRepository;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.student.service.StudentService;
import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import com.ibrahimsolak.studentloginapp.teacher.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;

    @Override
    public Grade submitGrade(Grade grade, Long courseId, Long studentId,Long teacherId) {
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);
        Teacher teacher = teacherService.getTeacherById(teacherId);
        if(!student.getCourses().contains(course)) {
            throw new StudentNotEnrolledException(studentId, courseId);
        } else if (!course.getTeacher().getId().equals(teacherId)) {
            throw new TeacherNotAssignedToCourseException(teacherId, courseId);
        }
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setTeacher(teacher);
        return gradeRepository.save(grade);
    }
}
