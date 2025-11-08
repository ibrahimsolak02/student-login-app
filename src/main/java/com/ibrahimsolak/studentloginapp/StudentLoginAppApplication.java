package com.ibrahimsolak.studentloginapp;

import com.ibrahimsolak.studentloginapp.role.Role;
import com.ibrahimsolak.studentloginapp.user.entity.User;
import com.ibrahimsolak.studentloginapp.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class StudentLoginAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentLoginAppApplication.class, args);
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("1234"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }
        };
    }

}
