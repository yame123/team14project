package com.sparta.team14project.user;

import com.sparta.team14project.message.MessageResponseDto;
import com.sparta.team14project.order.repository.CartRepository;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.user.repository.UserRepository;
import com.sparta.team14project.user.signup.dto.SignupRequestDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
    }

    @Test
    @DisplayName("신규 회원가입")
    void testSignup() {
        // 테스트 데이터 설정
        String username = "testuser";
        String email = "test@example.com";
        String password = "testpassword";
        boolean isOwner = false;

        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, email, "111111", isOwner);

        // Mock 객체 설정
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty()); // 중복 username을 가지는 유저가 없음
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // 서비스 메서드 호출
        userService.signup(signupRequestDto);

        // 테스트 검증
        verify(userRepository, times(1)).findByUsername("testuser"); // findByUsername 메서드가 1번 호출되었는지 검증
        verify(userRepository, times(1)).save(any(User.class)); // save 메서드가 1번 호출되었는지 검증

    }
}