package com.sparta.team14project.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.team14project.menu.dto.CookieMenuResponseDto;
import com.sparta.team14project.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StoreInfoResponseDto {
    @JsonProperty("storeName")
    private String storeName;
    @JsonProperty("storeDetails")
    private String storeDetails;
    @JsonProperty("storeAddress")
    private String storeAddress;
    @JsonProperty("menuList")
    private List<CookieMenuResponseDto> menuList;

    public StoreInfoResponseDto(Store store, List<CookieMenuResponseDto> menuResponseDtoList) {
        this.storeName = store.getStoreName();
        this.storeDetails = store.getStoreDetails();
        this.storeAddress = store.getStoreAddress();
        this.menuList = menuResponseDtoList;
    }
}
