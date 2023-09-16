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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order_id",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private  List<OrderedMenu> orderedMenuList;

    @Column(name = "username", nullable = false)
    private boolean delivered;

    @Column(name= "address",nullable = false)
    private String address;


    public void addMenu(OrderedMenu orderedMenu) {
        this.orderedMenuList.add(orderedMenu);
    }
}
