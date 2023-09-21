package com.sparta.team14project.user;

import com.sparta.team14project.message.MessageResponseDto;
import com.sparta.team14project.user.signup.dto.SignupRequestDto;
import com.sparta.team14project.order.entity.Cart;
import com.sparta.team14project.order.repository.CartRepository;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.user.entity.UserRoleEnum;
import com.sparta.team14project.user.repository.UserRepository;
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
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());


        //회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            return;
//            return new MessageResponseDto("중복된 username 입니다.",400);
        }

        //사용자 role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        Long userPoint = 1000000L;
        if (signupRequestDto.isOwner()) {
            role = UserRoleEnum.OWNER;
            userPoint = 0L;
        }

        //사용자 등록 =>입력한 이름과 암호화된 비밀번호 저장
        User user = new User(username, password, email, role, userPoint);
        userRepository.save(user);
        Cart cart = new Cart (user); // user 생성 시 장바구니 동시 생성
        cartRepository.save(cart);

//        MessageResponseDto messageResponseDto = new MessageResponseDto("회원가입 성공",200);
//        return messageResponseDto;
    }

}
