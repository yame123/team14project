package com.sparta.team14project.user;

import com.sparta.team14project.user.signup.dto.EmailRequestDto;
import com.sparta.team14project.message.MessageResponseDto;
import com.sparta.team14project.user.signup.dto.SignupRequestDto;
import com.sparta.team14project.user.signup.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    //회원가입 구현
//    @PostMapping("/user/signup")
//    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
//        //emailService.checkCode(signupRequestDto.getEmail(), signupRequestDto.getCode());
//        return userService.signup(signupRequestDto);
//    }

    @PostMapping("/user/signup")
    public String signup(@Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
//        emailService.checkCode(signupRequestDto.getEmail(), signupRequestDto.getCode());
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/user/signup";
        }
        userService.signup(signupRequestDto);
        return "redirect:/user/login-page";
    }


    @PostMapping("/user/mail")
    public ResponseEntity<String> mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto){
        emailService.sendEmail(emailRequestDto.getEmail());
        return ResponseEntity.ok("이메일 인증 요청이 전송되었습니다.");
    }
}
