package com.sparta.team14project.dto;


import com.sparta.team14project.entity.Order;
import com.sparta.team14project.entity.OrderedMenu;
import com.sparta.team14project.entity.User;
import jakarta.persistence.*;

import java.awt.*;
import java.util.Map;

public class OrderResponseDto {

    private long id;
    private String username;
    private String address;
    private Map<String,Integer> orderList;
    private boolean delivered;


    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.username = order.getUser().getUsername();
        this.address = order.getAddress();
        this.delivered = order.isDelivered();
        for(OrderedMenu om: order.getOrderedMenuList()){
            this.orderList.put(om.getMenu().getName(),om.getCount());
        }
    }
}
