package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false,unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "userRole", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    @Column(name = "userPoint")
    private Long userPoint;

    public User(String username, String password, String email, UserRoleEnum userRole, Long userPoint) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.userPoint = userPoint;
    }

}