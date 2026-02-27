package com.ibrahimsolak.studentloginapp.grade.controller;

import com.ibrahimsolak.studentloginapp.grade.entity.Grade;
import com.ibrahimsolak.studentloginapp.grade.service.GradeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/grade")
public class GradeController {

    private final GradeService gradeService;

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/{courseId}/student/{studentId}/teacher/{teacherId}")
    public ResponseEntity<Grade> submitGrade(@Valid @RequestBody Grade grade, @PathVariable Long courseId, @PathVariable Long studentId, @PathVariable Long teacherId) {
        return new ResponseEntity<>(gradeService.submitGrade(grade, courseId, studentId, teacherId), HttpStatus.CREATED);
    }
}
