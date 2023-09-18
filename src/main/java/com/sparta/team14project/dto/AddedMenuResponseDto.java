package com.sparta.team14project.dto;


import com.sparta.team14project.entity.AddedMenu;
import com.sparta.team14project.entity.Menu;

public class AddedMenuResponseDto {


    private String menuName;

    private int count;

    public AddedMenuResponseDto(AddedMenu addedMenu) {
        this.menuName = addedMenu.getMenu().getName();
        this.count = addedMenu.getCount();
    }
}
