package com.sparta.team14project.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/api/user/login-page")
    public String loginPage() {
        return "login"; // 로그인 페이지의 뷰 이름 (templates 디렉토리 아래의 HTML 파일 이름)
    }

    @GetMapping("/api/user/signup")
    public String signupPage() {
        return "signup"; // 로그인 페이지의 뷰 이름 (templates 디렉토리 아래의 HTML 파일 이름)
    }
}
