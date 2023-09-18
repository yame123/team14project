package com.sparta.team14project.dto;

import com.sparta.team14project.entity.AddedMenu;
import com.sparta.team14project.entity.Cart;

import java.util.ArrayList;
import java.util.List;


public class CartResponseDto {
    private Long id;

    private String username;

    private String storename;
    private List<AddedMenuResponseDto> addedMenuList = new ArrayList<>();

    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.username = cart.getUser().getUsername();
        this.storename = cart.getStore()==null?null:cart.getStore().getStoreName();
        for (AddedMenu am:cart.getAddedMenuList()){
            AddedMenuResponseDto tmp = new AddedMenuResponseDto(am);
            this.addedMenuList.add(tmp);
        }
    }
}
