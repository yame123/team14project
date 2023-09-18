package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "added_menu")
@NoArgsConstructor
public class AddedMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId")
    private Menu menu;

    private int count;


    public AddedMenu(Menu menu, Cart cart) {
        this.menu = menu;
        this.cart = cart;
        this.count = 1;
    }

    public void updateAddedMenu() {
        this.count++;
    }

    public void delupdateAddedMenu() {
        this.count--;
    }
}
