package com.sparta.team14project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false,unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "userRole", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    @Column(name = "userPoint")
    private Long userPoint;

    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.REMOVE},fetch = FetchType.LAZY)
    private Cart cart;


    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},fetch = FetchType.LAZY)
    private List<Delivery> deliveryList;

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE},fetch = FetchType.LAZY)
    private Store store;

    public User(String username, String password, String email, UserRoleEnum userRole, Long userPoint) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.userPoint = userPoint;
    }

    public void pay(int money) {
        this.userPoint -=money;
    }
}