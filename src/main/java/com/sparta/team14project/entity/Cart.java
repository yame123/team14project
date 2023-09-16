package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import java.awt.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "cart_id",cascade = {CascadeType.PERSIST },orphanRemoval = true)
    private List<AddedMenu> addedMenuList;

    public void resetCart(){
        this.store = null;
        this.addedMenuList.removeAll();
    }



}
