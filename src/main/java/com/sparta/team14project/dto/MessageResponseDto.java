package com.sparta.team14project.dto;

import lombok.Getter;

@Getter
public class MessageResponseDto {

    private String msg;
    private Integer statusCode;

    public MessageResponseDto(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}