package com.sparta.team14project.menu.dto;

import com.sparta.team14project.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private String detail;

    public MenuResponseDto(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.detail = menu.getDetail();
    }
}
