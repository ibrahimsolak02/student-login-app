package com.ibrahimsolak.studentloginapp.user.controller;

import com.ibrahimsolak.studentloginapp.user.entity.User;
import com.ibrahimsolak.studentloginapp.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id).getUsername(), HttpStatus.OK);
    }

    @PostMapping("/register/student")
    public ResponseEntity<User> saveUserAsStudent(@Valid @RequestBody User user) {
        userService.saveUserAsStudent(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<User> saveUserAsTeacher(@Valid @RequestBody User user) {
        userService.saveUserAsTeacher(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
