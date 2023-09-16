package com.sparta.team14project.dto;

import com.sparta.team14project.entity.Cart;
import com.sparta.team14project.entity.Store;
import com.sparta.team14project.entity.User;


import java.util.List;
import java.util.stream.Collectors;


public class CartResponseDto {
    private Long id;

    private User user;

    private Store store;
    private List<AddedMenuResponseDto> addedMenuList;

    public CartResponseDto(Cart cart) {
        this.user = cart.getUser();
        this.store = cart.getStore();
        this.addedMenuList = cart.getAddedMenuList().stream().map(x->new AddedMenuResponseDto(x)).collect(Collectors.toList());
    }
}
