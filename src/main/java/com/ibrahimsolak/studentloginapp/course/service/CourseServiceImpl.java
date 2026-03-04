package com.ibrahimsolak.studentloginapp.course.service;

import com.ibrahimsolak.studentloginapp.course.dto.CourseDTO;
import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.repository.CourseRepository;
import com.ibrahimsolak.studentloginapp.exception.EntityNotFoundException;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.student.service.StudentService;
import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import com.ibrahimsolak.studentloginapp.teacher.service.TeacherService;
import com.ibrahimsolak.studentloginapp.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;

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

    Course unWrapCourse(Optional<Course> course, Long id) {
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new EntityNotFoundException(id, Course.class);
        }
    }
}
