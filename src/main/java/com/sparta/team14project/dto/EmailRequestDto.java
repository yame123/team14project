package com.sparta.team14project.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class EmailRequestDto {
    @Email
    private String email;
}
