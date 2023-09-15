package com.sparta.team14project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rkdgusdnr1999@gmail.com");
        message.setTo(toEmail);
        message.setText("인증 코드: " + createVerificationCode()); // 내용
        message.setSubject("음식 배달 인증 코드"); // 제목

        mailSender.send(message);

        System.out.println("메일 전송 완료");
    }

    public String createVerificationCode() {
        // 6자리의 무작위 숫자 생성
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        String verificationCode = String.valueOf(code);

        return verificationCode;
    }
}
