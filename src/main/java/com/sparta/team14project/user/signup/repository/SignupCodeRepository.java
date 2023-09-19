package com.sparta.team14project.user.signup.repository;

import com.sparta.team14project.user.signup.entity.SignupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SignupCodeRepository extends JpaRepository<SignupCode, Long> {
    SignupCode findByEmail(String email);
    Optional<SignupCode> findByEmailAndCode(String email, String code);
}
