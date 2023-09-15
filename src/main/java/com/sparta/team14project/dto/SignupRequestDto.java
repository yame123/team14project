package com.sparta.team14project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 소문자 및 숫자로 구성된 4~10자의 문자열이어야 합니다.")
    private String username;
    @Pattern(regexp = "^[A-Za-z0-9@#$%^&+=!]{8,15}$", message = "비밀번호는 대소문자, 숫자, 특수문자(@#$%^&+=!)로만 구성된 8~15자의 문자열이어야 합니다.")
    private String password;

    @Email(message = "올바른 이메일 주소 형식이어야 합니다.")
    private String email;
    // 인증 코드
    private int certificationNumber;

    private boolean owner = false;
}