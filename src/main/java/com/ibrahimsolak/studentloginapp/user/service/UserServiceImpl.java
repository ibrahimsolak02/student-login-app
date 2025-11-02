package com.ibrahimsolak.studentloginapp.user.service;

import com.ibrahimsolak.studentloginapp.exception.EntityNotFoundException;
import com.ibrahimsolak.studentloginapp.role.Role;
import com.ibrahimsolak.studentloginapp.user.entity.User;
import com.ibrahimsolak.studentloginapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public User saveUserAsStudent(User user) {
        user.setRole(Role.STUDENT);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User saveUserAsTeacher(User user) {
        user.setRole(Role.TEACHER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
