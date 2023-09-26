package com.sparta.team14project.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private String name;
    private int price;
    private String details;
}
