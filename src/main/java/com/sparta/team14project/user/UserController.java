package com.sparta.team14project.user;

import com.sparta.team14project.user.signup.dto.EmailRequestDto;
import com.sparta.team14project.message.MessageResponseDto;
import com.sparta.team14project.user.signup.dto.SignupRequestDto;
import com.sparta.team14project.user.signup.EmailService;
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
    private final EmailService emailService;
    //회원가입 구현
    @PostMapping("/user/signup")
    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        //emailService.checkCode(signupRequestDto.getEmail(), signupRequestDto.getCode());
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/user/mail")
    public void mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto){
        emailService.sendEmail(emailRequestDto.getEmail());
    }

}
