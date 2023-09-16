package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "delivery",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<OrderedMenu> orderedMenuList;

    @Column(name = "username", nullable = false)
    private boolean delivered;

    @Column(name= "address",nullable = false)
    private String address;


    public void addMenu(OrderedMenu orderedMenu) {
        this.orderedMenuList.add(orderedMenu);
    }
}
