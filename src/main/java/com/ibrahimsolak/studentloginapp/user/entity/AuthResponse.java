package com.ibrahimsolak.studentloginapp.user.entity;

public record AuthResponse(
        User user,
        String token
) {}