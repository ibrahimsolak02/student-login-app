package com.ibrahimsolak.studentloginapp.course.controller;

import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course){
        courseService.saveCourse(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{courseId}/student/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Course> enrollToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        return new ResponseEntity<>(courseService.addStudentToCourse(courseId,studentId), HttpStatus.OK);
    }
}
