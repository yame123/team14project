package com.sparta.team14project.dto;

import com.sparta.team14project.entity.OrderReview;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewResponseDto {
    private String title;
    private int star;
    private String detail;
    private String image;
    private String username;
    private String storename;
    private List<String> menunamelist;

    public ReviewResponseDto(OrderReview review) {
        this.title = review.getTitle();
        this.star = review.getStar();
        this.detail = review.getDetails();
        this.image = review.getImage();
        this.username = review.getOrder().getUser().getUsername();
        this.storename = review.getOrder().getStore().getStoreName();
        this.menunamelist = review.getOrder().getOrderedMenuList(); // 뒤에 매핑해서 list 정보를 메뉴 이름만 저장하도록
    }
}
