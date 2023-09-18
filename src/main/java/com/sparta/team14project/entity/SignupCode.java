package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "signupCode")
public class SignupCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "expirationTime", nullable = false)
    private LocalDateTime expirationTime;

    @Column(name = "creationTime", nullable = false)
    private LocalDateTime creationTime;

    public SignupCode (String code, String email) {
        this.code = code;
        this.email = email;
        this.creationTime = LocalDateTime.now();
        this.expirationTime = this.creationTime.plusMinutes(5);
    }

    public void update(String code) {
        this.code = code;
        this.creationTime = LocalDateTime.now();
        this.expirationTime = this.creationTime.plusMinutes(5);
    }
}
