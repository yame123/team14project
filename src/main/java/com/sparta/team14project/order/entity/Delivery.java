package com.sparta.team14project.order.entity;

import com.sparta.team14project.order.dto.OrderRequestDto;
import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "delivery")
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "delivery", orphanRemoval = true)
    private List<OrderedMenu> orderedMenuList=new ArrayList<>();

    @Column(name = "delivered", nullable = false)
    private boolean delivered;

    @Column(name= "address",nullable = false)
    private String address;

    @Column(name = "price")
    private int price=0;

    public void addMenu(OrderedMenu orderedMenu) {
        this.orderedMenuList.add(orderedMenu);
    }

    public Delivery(OrderRequestDto requestDto, User user, Store store) {
        this.user = user;
        this.address = requestDto.getAddress();
        this.store = store;
    }

    public void deliveryDone() {
        this.delivered = true;
        this.store.earnPoint(this.price);
        this.store.getUser().earn(this.price);
    }


    public void payAndSetPrice(int money) {
        this.user.pay(money);
        this.price = money;

    }
}
