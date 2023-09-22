package com.sparta.team14project.store.entity;

import com.sparta.team14project.store.dto.StoreRequestDto;
import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    @Column(name = "star", nullable = false)
    private double star = 0;

    @Column(name = "reviewedPeople", nullable = false)
    private double reviewedPeople = 0;

    // storePoint (Long)
    @Column(name = "storePoint", nullable = false)
    private int storePoint = 0;

    @OneToMany(mappedBy = "store", orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    // ownerId (Long)
    @OneToOne(fetch = FetchType.LAZY)// cascade는 직접 지우면 사라지지 않지만 orphanRemoval은 사라진다.
    @JoinColumn(name = "owner_id")
    private User user;



    public Store(StoreRequestDto requestDto, User user){
        this.storeName = requestDto.getStoreName();
        this.storeDetails = requestDto.getStoreDetails();
        this.storeAddress = requestDto.getStoreAddress();
        this.user = user;
    }

    public void update(StoreRequestDto requestDto){
        this.storeName = requestDto.getStoreName();
        this.storeDetails = requestDto.getStoreDetails();
        this.storeAddress = requestDto.getStoreAddress();
    }

    public void earnPoint(int price) {
        this.storePoint += price;
    }

    public void addStar(int star) {
        this.star += star;
        this.reviewedPeople ++;
    }
}
