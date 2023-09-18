package com.sparta.team14project.dto;


import com.sparta.team14project.entity.AddedMenu;
import lombok.Getter;

@Getter
public class AddedMenuResponseDto {

    private MenuResponseDto menuResponseDto;

    private int count;

    public AddedMenuResponseDto(AddedMenu addedMenu) {
        this.menuResponseDto = new MenuResponseDto(addedMenu.getMenu());
        this.count = addedMenu.getCount();
    }
}
