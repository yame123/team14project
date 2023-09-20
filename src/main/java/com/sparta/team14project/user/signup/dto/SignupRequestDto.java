package com.sparta.team14project.user.signup.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 소문자 및 숫자로 구성된 4~10자의 문자열이어야 합니다.")
    private String username;
    @Pattern(regexp = "^[A-Za-z0-9@#$%^&+=!]{8,15}$", message = "비밀번호는 대소문자, 숫자, 특수문자(@#$%^&+=!)로만 구성된 8~15자의 문자열이어야 합니다.")
    private String password;

    @Email(message = "올바른 이메일 주소 형식이어야 합니다.")
    private String email;

    @Pattern(regexp = "^[0-9]{6}$", message = "코드는 6자리 숫자로 구성되어 있습니다.")
    private String code;

    private boolean owner = false;
}
