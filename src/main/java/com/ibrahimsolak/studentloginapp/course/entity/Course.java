package com.ibrahimsolak.studentloginapp.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibrahimsolak.studentloginapp.grade.entity.Grade;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course name cannot be blank")
    @NonNull
    @Column
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_enrollment",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id")
    )
    private Set<Student> students;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades;

}
