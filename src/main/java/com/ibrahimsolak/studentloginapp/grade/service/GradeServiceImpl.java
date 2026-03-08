package com.ibrahimsolak.studentloginapp.grade.service;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.service.CourseService;
import com.ibrahimsolak.studentloginapp.exception.StudentNotEnrolledException;
import com.ibrahimsolak.studentloginapp.exception.TeacherNotAssignedToCourseException;
import com.ibrahimsolak.studentloginapp.grade.dto.GradeDTO;
import com.ibrahimsolak.studentloginapp.grade.entity.Grade;
import com.ibrahimsolak.studentloginapp.grade.repository.GradeRepository;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.student.service.StudentService;
import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import com.ibrahimsolak.studentloginapp.teacher.service.TeacherService;
import com.ibrahimsolak.studentloginapp.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;


    @Override
    public void submitGrade(GradeDTO gradeDTO) {
        Student student = studentService.getStudentById(gradeDTO.getStudentId());
        Course course = courseService.getCourseById(gradeDTO.getCourseId());
        Teacher teacher = getCurrentTeacher();

        validateGradeSubmission(student, course, teacher);

        Grade grade = getGradeByStudentIdAndCourse(student.getId(), course.getId());

        if (grade == null ) {
            grade = new Grade();
            grade.setStudent(student);
            grade.setCourse(course);
            grade.setTeacher(teacher);
            grade.setScore(gradeDTO.getGrade());
        }
        grade.setScore(gradeDTO.getGrade());

        gradeRepository.save(grade);
    }

    public Grade getGradeByStudentIdAndCourse(Long studentId, Long courseId) {
        return gradeRepository.findFirstByStudentIdAndCourseIdOrderByIdDesc(studentId, courseId)
                .orElse(null);
    }

    private Teacher getCurrentTeacher() {
        Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId;
        if (principal instanceof User) currentUserId = ((User) principal).getId();
        else throw new RuntimeException("Could not determine user ID");

        return teacherService.getTeacherByUserId(currentUserId);
    }

    private void validateGradeSubmission(Student student, Course course, Teacher teacher) {
        if (!student.getCourses().contains(course)) {
            throw new StudentNotEnrolledException(student.getId(), course.getId());
        }
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new TeacherNotAssignedToCourseException(teacher.getId(), course.getId());
        }
    }
}
