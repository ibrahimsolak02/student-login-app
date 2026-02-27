package com.ibrahimsolak.studentloginapp.exception;

public class TeacherNotAssignedToCourseException extends RuntimeException {
    public TeacherNotAssignedToCourseException(Long teacherId, Long courseId) {
        super("The teacher with id: '" + teacherId + "' is not assigned to the course with id: '" + courseId);
    }
}
