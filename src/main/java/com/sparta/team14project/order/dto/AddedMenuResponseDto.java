package com.sparta.team14project.order.dto;


import com.sparta.team14project.order.entity.AddedMenu;
import com.sparta.team14project.menu.dto.MenuResponseDto;
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
