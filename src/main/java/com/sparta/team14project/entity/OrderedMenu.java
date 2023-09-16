package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class OrderedMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int count;

    public OrderedMenu(AddedMenu am) {
        this.menu = am.getMenu();
        this.count = am.getCount();
    }
}