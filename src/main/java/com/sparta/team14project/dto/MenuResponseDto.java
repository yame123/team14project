package com.sparta.team14project.dto;

import com.sparta.team14project.entity.Menu;
import lombok.Getter;

@Getter
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private String details;

    public MenuResponseDto(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.details = menu.getDetails();
    }
}
