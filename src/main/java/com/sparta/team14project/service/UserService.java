package com.sparta.team14project.service;

import com.sparta.team14project.dto.SignupRequestDto;
import com.sparta.team14project.dto.SignupResponseDto;
import com.sparta.team14project.entity.Cart;
import com.sparta.team14project.entity.User;
import com.sparta.team14project.entity.UserRoleEnum;
import com.sparta.team14project.repository.CartRepository;
import com.sparta.team14project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

    //회원가입 기능
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());


        //회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return new SignupResponseDto("중복된 username 입니다.",400);
        }

        //사용자 role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        Long userPoint = 1000000L;
        if (signupRequestDto.isOwner()) {
            role = UserRoleEnum.OWNER;
            userPoint = 0L;
        }

        System.out.println("여기가 문젠가");

        //사용자 등록 =>입력한 이름과 암호화된 비밀번호 저장
        User user = new User(username, password, email, role, userPoint);
        userRepository.save(user);
        Cart cart = new Cart (user);
        cartRepository.save(cart);

        SignupResponseDto signupResponseDto = new SignupResponseDto("회원가입 성공",200);

        return signupResponseDto;
    }

}
