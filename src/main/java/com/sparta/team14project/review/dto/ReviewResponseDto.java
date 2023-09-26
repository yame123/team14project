package com.sparta.team14project.review.dto;

import com.sparta.team14project.review.entity.OrderReview;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewResponseDto {
    private String title;
    private int star;
    private String detail;
    private String username;
    private String storename;
    private List<String> menunamelist;

    public ReviewResponseDto(OrderReview review) {
        this.title = review.getTitle();
        this.star = review.getStar();
        this.detail = review.getDetails();
        this.username = review.getDelivery().getUser().getUsername();
        this.storename = review.getDelivery().getStore().getStoreName();
        this.menunamelist = review.getDelivery().getOrderedMenuList().stream().map(i->i.getMenu().getName()).collect(Collectors.toList()); // 뒤에 매핑해서 list 정보를 메뉴 이름만 저장하도록
    }
}
