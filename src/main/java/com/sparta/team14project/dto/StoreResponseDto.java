package com.sparta.team14project.dto;

import com.sparta.team14project.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponseDto {
    private Long id;
    private String storeName;
    private String storeDetails;
    private String storeAddress;
    private Float avgStar;
    private int storePoint;

    public StoreResponseDto(Store store){
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeDetails = store.getStoreDetails();
        this.storeAddress = store.getStoreAddress();
        this.avgStar = store.getAvgStar();
        this.storePoint = store.getStorePoint();
    }

}
