package com.sparta.team14project.dto;

import com.sparta.team14project.entity.AddedMenu;
import com.sparta.team14project.entity.Cart;
import com.sparta.team14project.entity.Order;
import com.sparta.team14project.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.awt.*;

public class CartResponseDto {
    private Long id;

    private User user;

    private Store store;
    private List<AddedMenuResponseDto> addedMenuList;

    public CartResponseDto(Cart cart) {
        this.user = cart.getUser();
        this.store = cart.getStore();
        this.addedMenuList = cart.getAddedMenuList();
    }
}
