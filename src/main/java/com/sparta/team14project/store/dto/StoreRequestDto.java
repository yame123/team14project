package com.sparta.team14project.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreRequestDto {
    private String storeName;
    private String storeDetails;
    private String storeAddress;
}