package com.sparta.team14project.menu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.team14project.menu.dto.MenuRequestDto;
import com.sparta.team14project.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "menu")
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "detail", nullable = false)
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(MenuRequestDto requestDto, Store store){
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.detail = requestDto.getDetail();
        this.store = store;
    }

    public void update(MenuRequestDto requestDto) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.detail = requestDto.getDetail();
    }
}
