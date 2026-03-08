package com.ibrahimsolak.studentloginapp.course.service;

import com.ibrahimsolak.studentloginapp.course.dto.CourseDTO;
import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.projection.CourseEnrollmentView;
import com.ibrahimsolak.studentloginapp.course.repository.CourseRepository;
import com.ibrahimsolak.studentloginapp.exception.EntityNotFoundException;
import com.ibrahimsolak.studentloginapp.grade.dto.GradeDTO;
import com.ibrahimsolak.studentloginapp.grade.entity.Grade;
import com.ibrahimsolak.studentloginapp.grade.service.GradeService;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.student.service.StudentService;
import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import com.ibrahimsolak.studentloginapp.teacher.service.TeacherService;
import com.ibrahimsolak.studentloginapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;
    @Lazy
    @Autowired
    private GradeService gradeService;

    @Override
    public Course saveCourse(Course course) {
        Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId;
        if (principal instanceof User) currentUserId = ((User) principal).getId();
        else throw new RuntimeException("Could not determine user ID");

        Teacher teacher = teacherService.getTeacherByUserId(currentUserId);
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course addStudentToCourse(Long courseId) {
        Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId;
        if (principal instanceof User) currentUserId = ((User) principal).getId();
        else throw new RuntimeException("Could not determine user ID");

        Course course = getCourseById(courseId);
        Student student = studentService.getStudentByUserId(currentUserId);
        course.getStudents().add(student);
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return unWrapCourse(course, id);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = (List<Course>) courseRepository.findAll();

        return courses.stream()
                .map(course -> {
                    CourseDTO dto = new CourseDTO();
                    dto.setId(course.getId());
                    dto.setName(course.getName());
                    dto.setTeacherName(course.getTeacher().getName());
                    dto.setDescription(course.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getEnrolledCourses() {
        Object principal  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId;
        if (principal instanceof User) currentUserId = ((User) principal).getId();
        else throw new RuntimeException("Could not determine user ID");

        Student student = studentService.getStudentByUserId(currentUserId);
        List<Course> courses = courseRepository.findByStudentsId(student.getId());

        return courses
                .stream()
                    .map(
                        course ->  {
                            CourseDTO dto = new CourseDTO();
                            dto.setId(course.getId());
                            dto.setName(course.getName());
                            dto.setTeacherName(course.getTeacher().getName());
                            dto.setDescription(course.getDescription());
                            return dto;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public List<GradeDTO> getAllEnrollments() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId;

        if (principal instanceof User user) {
            currentUserId = user.getId();
        } else {
            throw new RuntimeException("Could not determine user ID");
        }

        Long teacherId = teacherService.getTeacherByUserId(currentUserId).getId();
        List<Course> courseList = courseRepository.findByTeacherId(teacherId);

        if (courseList == null || courseList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> courseIdList = courseList.stream()
                .map(Course::getId)
                .distinct()
                .toList();

        List<CourseEnrollmentView> enrollmentList = courseRepository.findEnrollmentViewsByCourseIds(courseIdList);

        if (enrollmentList == null || enrollmentList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, Course> courseMap = courseList.stream()
                .collect(Collectors.toMap(Course::getId, c -> c, (existing, replacement) -> existing));

        List<Long> studentIds = enrollmentList.stream()
                .map(CourseEnrollmentView::getStudentId)
                .distinct()
                .toList();

        Map<Long, Student> studentMap = studentService.getStudentsByIdIn(studentIds);

        return enrollmentList.stream()
                .map(view -> {
                    GradeDTO dto = new GradeDTO();
                    Course c = courseMap.get(view.getCourseId());
                    Student s = studentMap.get(view.getStudentId());

                    dto.setCourseId(view.getCourseId());
                    dto.setStudentId(view.getStudentId());

                    Grade grade = gradeService.getGradeByStudentIdAndCourse(s.getId(), c.getId());

                    if (grade != null && grade.getScore() != null && !grade.getScore().isEmpty()) {
                        dto.setGrade(grade.getScore());
                    }

                    if (c != null) dto.setCourseName(c.getName());
                    if (s != null) dto.setStudentName(s.getName());

                    return dto;
                })
                .toList();
    }

    Course unWrapCourse(Optional<Course> course, Long id) {
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new EntityNotFoundException(id, Course.class);
        }
    }
}
