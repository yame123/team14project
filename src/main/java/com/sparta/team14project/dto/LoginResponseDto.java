package com.sparta.team14project.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String msg;
    private Integer statusCode;

    public LoginResponseDto(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}