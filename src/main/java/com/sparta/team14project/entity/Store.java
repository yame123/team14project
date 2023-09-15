package com.sparta.team14project.entity;

import com.sparta.team14project.dto.StoreRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "store")
public class Store {
    // storeId (Long)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // storeName (String)
    @Column(name = "storeName", nullable = false)
    private String storeName;
    // storeDetails (String)
    @Column(name = "storeDetails", nullable = false)
    private String storeDetails;
    // storeAddress (String)
    @Column(name = "storeAddress", nullable = false)
    private String storeAddress;
    // avgStar (Long)
    @Column(name = "avgStar", nullable = false)
    private Float avgStar;
    // storePoint (Long)
    @Column(name = "storePoint", nullable = false)
    private Long storePoint;

    // ownerId (Long)
    @OneToOne(orphanRemoval = true) // cascade는 직접 지우면 사라지지 않지만 orphanRemoval은 사라진다.
    @JoinColumn(name = "owner_id")
    private User user;

    public Store(StoreRequestDto requestDto){
        this.storeName = requestDto.getStoreName();
        this.storeDetails = requestDto.getStoreDetails();
        this.storeAddress = requestDto.getStoreAddress();
        this.avgStar = requestDto.getAvgStar();
        this.storePoint = requestDto.getStorePoint();
    }

    public void update(StoreRequestDto requestDto){
        this.storeName = requestDto.getStoreName();
        this.storeDetails = requestDto.getStoreDetails();
        this.storeAddress = requestDto.getStoreAddress();
        this.avgStar = requestDto.getAvgStar();
        this.storePoint = requestDto.getStorePoint();
    }
}
