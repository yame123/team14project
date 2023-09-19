package com.sparta.team14project.order.dto;


import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.order.entity.OrderedMenu;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OrderResponseDto {

    private long id;
    private String username;
    private String address;
    private Map<String,Integer> orderList = new HashMap<>();
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
