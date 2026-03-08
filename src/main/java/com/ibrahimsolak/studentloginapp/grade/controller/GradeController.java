package com.ibrahimsolak.studentloginapp.grade.controller;

import com.ibrahimsolak.studentloginapp.grade.dto.GradeDTO;
import com.ibrahimsolak.studentloginapp.grade.dto.GradeViewDTO;
import com.ibrahimsolak.studentloginapp.grade.service.GradeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/grade")
public class GradeController {

    private final GradeService gradeService;

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/submit")
    public ResponseEntity<GradeDTO> submitGrade(@Valid @RequestBody GradeDTO gradeDTO) {
        gradeService.submitGrade(gradeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/get-my-grades")
    public ResponseEntity<List<GradeViewDTO>>  getMyGrades() {
        List<GradeViewDTO> grades = gradeService.getMyGrades();
        return ResponseEntity.ok(grades);
    }
}
