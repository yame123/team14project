package com.sparta.team14project.user.entity;

import com.sparta.team14project.order.entity.Delivery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Delivery> deliveryList = new ArrayList<>();

    public User(String username, String password, String email, UserRoleEnum userRole, Long userPoint) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.userPoint = userPoint;
    }

    public void pay(int money) {
        this.userPoint -= money;
    }

    public void earn(int money) { this.userPoint += money; }
}