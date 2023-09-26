package com.sparta.team14project.store.dto;

import com.sparta.team14project.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CookieStoreResponseDto{
    private String id;
    private String storeName;
    private String storeDetails;
    private String storeAddress;
    private String avgStar;
    private String storePoint;

    public CookieStoreResponseDto(Store store){
        this.id = Long.toString(store.getId());
        this.storeName = store.getStoreName();
        this.storeDetails = store.getStoreDetails();
        this.storeAddress = store.getStoreAddress();
        this.avgStar = Double.toString(store.getReviewedPeople() != 0 ? store.getStar() / store.getReviewedPeople() : 0);
        this.storePoint = Integer.toString(store.getStorePoint());
    }
}
