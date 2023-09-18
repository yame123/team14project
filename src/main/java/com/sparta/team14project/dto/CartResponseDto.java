package com.sparta.team14project.dto;

import com.sparta.team14project.entity.AddedMenu;
import com.sparta.team14project.entity.Cart;
import com.sparta.team14project.entity.Store;
import com.sparta.team14project.entity.User;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Getter
public class CartResponseDto {
    private Long id;

    private User user;

    private Store store;
    private List<AddedMenuResponseDto> addedMenuList = new ArrayList<>();

    public CartResponseDto(Cart cart) {
        this.user = cart.getUser();
        this.store = cart.getStore();
        System.out.println("$$$$$$$$$$$$$$$$$ CartResponseDto $$$$$$$$$$$$$$$$$$$");
        for (AddedMenu am:cart.getAddedMenuList()){
            System.out.println("$$$$$$$$$$$$$$$$$ CartResponseDto 반복문 $$$$$$$$$$$$$$$$$$$");
            this.addedMenuList.add(new AddedMenuResponseDto(am));
        }
    }
}
