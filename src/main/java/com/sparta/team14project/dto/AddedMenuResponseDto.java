package com.sparta.team14project.dto;


import com.sparta.team14project.entity.AddedMenu;
import com.sparta.team14project.entity.Menu;

public class AddedMenuResponseDto {


    private Menu menu;

    private int count;

    public AddedMenuResponseDto(AddedMenu addedMenu) {
        this.menu = addedMenu.getMenu();
        this.count = addedMenu.getCount();
    }
}
