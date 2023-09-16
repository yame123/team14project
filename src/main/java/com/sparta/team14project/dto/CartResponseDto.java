package com.sparta.team14project.dto;

import com.sparta.team14project.entity.AddedMenu;
import com.sparta.team14project.entity.Cart;
import com.sparta.team14project.entity.Store;
import com.sparta.team14project.entity.User;


import java.util.List;


public class CartResponseDto {
    private Long id;

    private User user;

    private Store store;
    private List<AddedMenuResponseDto> addedMenuList;

    public CartResponseDto(Cart cart) {
        this.user = cart.getUser();
        this.store = cart.getStore();
        for (AddedMenu am:cart.getAddedMenuList()){
            this.addedMenuList.add(new AddedMenuResponseDto(am));
        }
    }
}
