package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class AddedMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int count;


    public AddedMenu(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }
}
