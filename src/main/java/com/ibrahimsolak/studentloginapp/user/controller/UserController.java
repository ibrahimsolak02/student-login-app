package com.ibrahimsolak.studentloginapp.user.controller;

import com.ibrahimsolak.studentloginapp.security.SecurityConstants;
import com.ibrahimsolak.studentloginapp.user.entity.AuthResponse;
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
    public ResponseEntity<AuthResponse> saveUserAsStudent(@Valid @RequestBody User user) {
        AuthResponse authResponse = userService.saveUserAsStudent(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + authResponse.token())
                .body(authResponse);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<AuthResponse> saveUserAsTeacher(@Valid @RequestBody User user) {
        AuthResponse authResponse = userService.saveUserAsTeacher(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + authResponse.token())
                .body(authResponse);
    }
}