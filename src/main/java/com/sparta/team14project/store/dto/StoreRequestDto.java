package com.sparta.team14project.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
    @NotBlank
    private String storeName;
    @NotBlank
    private String storeDetails;
    @NotBlank
    private String storeAddress;
}
