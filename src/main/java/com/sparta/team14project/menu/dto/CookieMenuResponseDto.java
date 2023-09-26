package com.sparta.team14project.menu.dto;

import com.sparta.team14project.menu.entity.Menu;
import lombok.Getter;

@Getter
public class CookieMenuResponseDto {
    private String id;
    private String name;
    private String price;
    private String details;

    public CookieMenuResponseDto(Menu menu){
        this.id = Long.toString(menu.getId());
        this.name = menu.getName();
        this.price = Integer.toString(menu.getPrice());
        this.details = menu.getDetails();
    }
}
