package com.ibrahimsolak.studentloginapp.user.service;

import com.ibrahimsolak.studentloginapp.user.entity.AuthResponse;
import  com.ibrahimsolak.studentloginapp.user.entity.User;

public interface UserService {
    User getUserById(Long id);

    User getUserByUsername(String username);

    AuthResponse saveUserAsStudent(User user);

    AuthResponse saveUserAsTeacher(User user);
}
