package com.sparta.team14project.order.dto;

import com.sparta.team14project.order.entity.AddedMenu;
import com.sparta.team14project.order.entity.Cart;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartResponseDto {
    private Long id;

    private String username;

    private List<AddedMenuResponseDto> addedMenuList = new ArrayList<>();

    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.username = cart.getUser().getUsername();
        for (AddedMenu am:cart.getAddedMenuList()){
            this.addedMenuList.add(new AddedMenuResponseDto(am));
        }
    }
}
