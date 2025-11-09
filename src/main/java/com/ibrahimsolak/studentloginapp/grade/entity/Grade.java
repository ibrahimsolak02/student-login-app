package com.ibrahimsolak.studentloginapp.grade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibrahimsolak.studentloginapp.course.entity.Course;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.validation.Score;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grade")
@Getter
@Setter
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Score
    private String score;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}
