package com.sparta.team14project.order.dto;

import com.sparta.team14project.menu.dto.MenuResponseDto;
import com.sparta.team14project.order.entity.OrderedMenu;
import lombok.Getter;

@Getter
public class OrderedMenuResponseDto {
    private MenuResponseDto menuResponseDto;
    private int count;

    public OrderedMenuResponseDto(OrderedMenu orderedMenu) {
        this.menuResponseDto = new MenuResponseDto(orderedMenu.getMenu());
        this.count = orderedMenu.getCount();
    }
}
