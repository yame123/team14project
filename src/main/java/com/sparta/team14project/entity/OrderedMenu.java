package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.awt.*;

@Getter
public class OrderedMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private  Menu menu;

    private int count;

    public OrderedMenu(AddedMenu am) {
        this.menu = am.getMenu();
        this.count = am.getCount();
    }
}