package com.ibrahimsolak.studentloginapp.grade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeViewDTO {
    private Long courseId;
    private String courseName;
    private String grade;
}
