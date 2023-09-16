package com.sparta.team14project.controller;

import com.sparta.team14project.dto.EmailRequestDto;
import com.sparta.team14project.dto.SignupRequestDto;
import com.sparta.team14project.dto.MessageResponseDto;
import com.sparta.team14project.service.EmailService;
import com.sparta.team14project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    //회원가입 구현
    @PostMapping("/user/signup")
    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        emailService.checkCode(signupRequestDto.getEmail(), signupRequestDto.getCode());
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/user/mail")
    public void mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto){
        emailService.sendEmail(emailRequestDto.getEmail());
    }

}
