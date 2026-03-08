package com.ibrahimsolak.studentloginapp.grade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeDTO {
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseName;
    private String grade;
}
