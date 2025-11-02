package com.ibrahimsolak.studentloginapp.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Username cannot be blank")
    @Column(nullable = false)
    private String username;

    @NonNull
    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

}
