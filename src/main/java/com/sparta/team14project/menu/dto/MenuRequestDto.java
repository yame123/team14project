package com.sparta.team14project.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuRequestDto {
    private String name;
    private int price;
    private String details;
}
