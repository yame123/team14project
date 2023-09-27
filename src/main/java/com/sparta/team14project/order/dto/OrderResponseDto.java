package com.sparta.team14project.order.dto;


import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.order.entity.OrderedMenu;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponseDto {

    private String username;
    private Long userid;
    private String storename;
    private Long storeownerid;
    private String address;
    private List<OrderedMenuResponseDto> orderList = new ArrayList<>();
    private boolean delivered;

    public OrderResponseDto(Delivery delivery) {

        this.username = delivery.getUser().getUsername();
        this.userid = delivery.getUser().getId();
        this.storename = delivery.getStore().getStoreName();
        this.storeownerid = delivery.getStore().getUser().getId();
        this.address = delivery.getAddress();
        this.delivered = delivery.isDelivered();
        for(OrderedMenu om: delivery.getOrderedMenuList()){
            this.orderList.add(new OrderedMenuResponseDto(om));
        }
    }
}
