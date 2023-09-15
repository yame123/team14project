package com.sparta.team14project.controller;

import com.sparta.team14project.dto.EmailRequestDto;
import com.sparta.team14project.dto.SignupRequestDto;
import com.sparta.team14project.dto.SignupResponseDto;
import com.sparta.team14project.service.EmailSenderService;
import com.sparta.team14project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final EmailSenderService emailSenderService;
    //회원가입 구현
    @PostMapping("/user/signup")
    public SignupResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/user/mail")
    public void MailSend(@RequestBody @Valid EmailRequestDto emailRequestDto){
        emailSenderService.sendEmail(emailRequestDto.getEmail());
    }
}
