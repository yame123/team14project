package com.sparta.team14project.dto;


import com.sparta.team14project.entity.AddedMenu;
import com.sparta.team14project.entity.Menu;
import lombok.Getter;

@Getter
public class AddedMenuResponseDto {


    private Menu menu;

    private int count;

    public AddedMenuResponseDto(AddedMenu addedMenu) {
        System.out.println("$$$$$$$$$$$$$ AddedMenuResponseDto getMenu() $$$$$$$$$$$$$");
        this.menu = addedMenu.getMenu();
        System.out.println("$$$$$$$$$$$$$ AddedMenuResponseDto getCount() $$$$$$$$$$$$$");
        this.count = addedMenu.getCount();
    }
}
