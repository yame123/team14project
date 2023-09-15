package com.sparta.team14project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private float avgStar;
    // storePoint (Long)
    @Column(name = "storePoint", nullable = false)
    private Long storePoint;

    // ownerId (Long)
//    @OneToOne
//    @JoinColumn(name = "owner_id")
//    private Cart cart;
}
