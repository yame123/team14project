package com.sparta.team14project.store.dto;

import com.sparta.team14project.menu.dto.MenuResponseDto;
import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.review.dto.ReviewResponseDto;
import com.sparta.team14project.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StoreMenuResponseDto {
    private Long id;
    private String storeName;
    private String storeDetails;
    private String storeAddress;
    private double avgStar;
    private int storePoint;
    private List<MenuResponseDto> menuList;

    public StoreMenuResponseDto(Store store){
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeDetails = store.getStoreDetails();
        this.storeAddress = store.getStoreAddress();
        this.avgStar = store.getReviewedPeople()!=0?store.getStar()/store.getReviewedPeople():0;
        this.storePoint = store.getStorePoint();
        this.menuList = menuToDto(store.getMenuList());
    }

    public List<MenuResponseDto> menuToDto(List<Menu> menuList){
        return menuList.stream().map(MenuResponseDto::new).collect(Collectors.toList());
    }

}
