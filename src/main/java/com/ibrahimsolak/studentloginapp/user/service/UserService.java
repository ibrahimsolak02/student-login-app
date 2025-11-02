package com.ibrahimsolak.studentloginapp.user.service;

import  com.ibrahimsolak.studentloginapp.user.entity.User;

public interface UserService {
    User getUserById(Long id);

    User getUserByUsername(String username);

    User saveUserAsStudent(User user);

    User saveUserAsTeacher(User user);
}
