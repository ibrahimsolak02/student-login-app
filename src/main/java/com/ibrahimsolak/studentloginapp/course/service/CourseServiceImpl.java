package com.ibrahimsolak.studentloginapp.course.service;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.repository.CourseRepository;
import com.ibrahimsolak.studentloginapp.exception.EntityNotFoundException;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.student.service.StudentService;
import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import com.ibrahimsolak.studentloginapp.teacher.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Override
    public Course saveCourse(Course course, Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course addStudentToCourse(Long courseId, Long studentId) {
        Course course = getCourseById(courseId);
        Student student = studentService.getStudentById(studentId);
        course.getStudents().add(student);
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return unWrapCourse(course, id);
    }

    @Override
    public List<Course> getAllCourses() {
        return (List<Course>)courseRepository.findAll();
    }

    Course unWrapCourse(Optional<Course> course, Long id) {
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new EntityNotFoundException(id, Course.class);
        }
    }
}
