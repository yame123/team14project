package com.sparta.team14project.entity;

import com.sparta.team14project.dto.MenuRequestDto;
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
    private long price;
    @Column(name = "details", nullable = false)
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store")
    private Store store;

    public Menu(MenuRequestDto requestDto, Store store){
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.details = requestDto.getDetails();
        this.store = store;
    }

    public void update(MenuRequestDto requestDto) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.details = requestDto.getDetails();
    }
}
