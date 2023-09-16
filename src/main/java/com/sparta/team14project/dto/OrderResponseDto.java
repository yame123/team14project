package com.sparta.team14project.dto;


import com.sparta.team14project.entity.Delivery;
import com.sparta.team14project.entity.OrderedMenu;

import java.util.Map;

public class OrderResponseDto {

    private long id;
    private String username;
    private String address;
    private Map<String,Integer> orderList;
    private boolean delivered;


    public OrderResponseDto(Delivery delivery) {
        this.id = delivery.getId();
        this.username = delivery.getUser().getUsername();
        this.address = delivery.getAddress();
        this.delivered = delivery.isDelivered();
        for(OrderedMenu om: delivery.getOrderedMenuList()){
            this.orderList.put(om.getMenu().getName(),om.getCount());
        }
    }
}
