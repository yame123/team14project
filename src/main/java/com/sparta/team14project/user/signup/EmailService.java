package com.sparta.team14project.user.signup;

import com.sparta.team14project.user.signup.entity.SignupCode;
import com.sparta.team14project.user.signup.repository.SignupCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SignupCodeRepository signupCodeRepository;

    @Transactional
    // 인증 메일 전송 및 인증 정보 저장
    public void sendEmail(String toEmail) {
        String verificationCode = createVerificationCode();
        SignupCode existCode = signupCodeRepository.findByEmail(toEmail);
        if (existCode != null) { // DB에 인증 정보 있다면 update 수행
           existCode.update(verificationCode);
        }
        else { // 없다면 새로 만듦
            SignupCode signupCode = new SignupCode(verificationCode, toEmail);
            signupCodeRepository.save(signupCode);
        }

        // 메일 전송 형식 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rkdgusdnr1999@gmail.com");
        message.setTo(toEmail);
        message.setText("인증 코드: " + verificationCode); // 내용
        message.setSubject("음식 배달 인증 코드"); // 제목

        mailSender.send(message);
        System.out.println("메일 전송 완료");
    }

    public void checkCode(String email,String userCode) {
        SignupCode signupCode = signupCodeRepository.findByEmailAndCode(email, userCode).orElseThrow(() ->
            new IllegalArgumentException("유효하지 않은 코드입니다.")
        );

        // 코드 유효시간 지났는지 체크
        LocalDateTime currentTime = LocalDateTime.now();
        if (signupCode.getExpirationTime().isBefore(currentTime)) {
            signupCodeRepository.delete(signupCode); // 만료되었으면 삭제
            throw new IllegalArgumentException("유효 기간이 만료된 코드입니다.");
        }
        // check와 동시에 삭제
        signupCodeRepository.delete(signupCode);
    }

    public String createVerificationCode() {
        // 6자리의 무작위 숫자 생성
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        String verificationCode = String.valueOf(code);

        return verificationCode;
    }
}
