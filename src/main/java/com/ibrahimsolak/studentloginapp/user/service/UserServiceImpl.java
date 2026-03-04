package com.ibrahimsolak.studentloginapp.user.service;

import com.ibrahimsolak.studentloginapp.exception.EntityNotFoundException;
import com.ibrahimsolak.studentloginapp.role.Role;
import com.ibrahimsolak.studentloginapp.security.JwtService;
import com.ibrahimsolak.studentloginapp.student.entity.Student;
import com.ibrahimsolak.studentloginapp.teacher.entity.Teacher;
import com.ibrahimsolak.studentloginapp.user.entity.AuthResponse;
import com.ibrahimsolak.studentloginapp.user.entity.User;
import com.ibrahimsolak.studentloginapp.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return unwrap(userOptional,id);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return unwrap(userOptional,username);
    }

    @Override
    public AuthResponse saveUserAsStudent(User user) {

        if(userRepository.existsByUsername(user.getUsername())){
            throw new EntityExistsException("Username already exists");
        }

        user.setRole(Role.STUDENT);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Student student = new Student();
        student.setName(user.getUsername());
        student.setUser(user);
        user.setStudent(student);
        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getUsername(), List.of(String.valueOf(Role.STUDENT)));
        return new AuthResponse(savedUser,token);
    }

    @Override
    public AuthResponse saveUserAsTeacher(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new EntityExistsException("Username already exists");
        }

        user.setRole(Role.TEACHER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Teacher teacher = new Teacher();
        teacher.setName(user.getUsername());
        teacher.setUser(user);
        user.setTeacher(teacher);
        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getUsername(), List.of(String.valueOf(Role.TEACHER)));
        return new AuthResponse(savedUser,token);
    }

    static User unwrap(Optional<User> user, Long id) {
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException(id, User.class);
        }
    }

    static User unwrap(Optional<User> user, String username) {
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException(username, User.class);
        }
    }
}
